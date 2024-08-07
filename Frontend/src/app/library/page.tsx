    "use client"

    import { useEffect, useState } from "react";
    import { useWork } from "../lib/CustomHooks/useWork";
    import ReactPaginate from "react-paginate";
    import { useSearchParams, useRouter } from 'next/navigation';

    interface Params {
        [key: string]: string | number;
    }

    export default function Library() {
        const { getWorks, getWorksBySearch, loading, pageInfo } = useWork();
        const [itemsPerPage, setItemsPerPage] = useState<number>(10); // Establecer un valor predeterminado
        const [search, setSearch] = useState<string>("");
        const [currentPage, setCurrentPage] = useState<number>(1);
        const [workType, setWorkType] = useState<number>(1);
        const [paramsLoaded, setParamsLoaded] = useState<boolean>(false); 
        const searchParams = useSearchParams();
        const router = useRouter();

        useEffect(() => {
            // Leer parámetros de la URL
            const searchValue = searchParams.get('search') || search;
            const workTypeValue = searchParams.get('workType') || workType.toString();
            const currentPageValue = searchParams.get('page') || currentPage.toString();

            // Actualizar estado con los parámetros leídos
            setSearch(searchValue);
            setWorkType(parseInt(workTypeValue));
            setCurrentPage(parseInt(currentPageValue));

            // Marcar los parámetros como cargados
            setParamsLoaded(true);
        }, []);

        useEffect(() => {
            console.log("llamando: " +currentPage)
            if (paramsLoaded) {
                // Solo llamar a getWorks si los parámetros han sido cargados
                getWorks(workType, currentPage-1);
            }
        }, [ currentPage,workType, paramsLoaded]); 

      

        const updateUrlParams = (params: Params) => {
            const newSearchParams = new URLSearchParams(searchParams); 
            Object.keys(params).forEach(key => {
                if (params[key] !== undefined) {
                    newSearchParams.set(key, params[key].toString());
                }
            });


            router.push(`?${newSearchParams.toString()}`);
        };

        const pagginationHandler = (page: { selected: number }) => {
            console.log("page selected: "+ page.selected)
            setCurrentPage(page.selected+1);
            updateUrlParams({ page: page.selected+1 });
        };

        const handleWorkType = (workTypeId: number) => {
            setWorkType(workTypeId);
            setCurrentPage(1);
            updateUrlParams({ workType: workTypeId, page: 1 });
        };

        const handleSearch = () => {
            getWorksBySearch(search);
            updateUrlParams({ search: search });
        }

        return (
            <div className="library-body">
                <div className="title-section-container">
                    <div className="library-categories">
                        <nav>
                            <ul>
                                <li onClick={() => handleWorkType(1)}>Anime</li>
                                <li onClick={() => handleWorkType(2)}>Mangas</li>
                                <li onClick={() => handleWorkType(3)}>Novelas</li>
                            </ul>
                        </nav>
                    </div>
                    <main>
                        <div className="search-work">
                            <input 
                                type="text" 
                                value={search} 
                                onChange={(e) => {
                                    setSearch(e.target.value);
                                    updateUrlParams({ search: e.target.value });
                                }} 
                                placeholder="Search..."
                            />
                            <button onClick={handleSearch}>search</button>
                        </div>
                        {loading && pageInfo?.content ? (
                            <span>works are loading...</span>
                        ) : (
                            <div className="works">
                                <ul>
                                    {pageInfo?.content.map((work) => (
                                        <li className="work" key={work.id}>
                                            <span> {work.title}</span>
                                            <img src={work.image} alt={work.title} /> 
                                            <span>{work.workType}</span>
                                        </li>
                                    ))}
                                </ul>
                            </div>
                        )}
                        <div className="paginable">
                            <ReactPaginate
                                previousLabel={'previous'}
                                nextLabel={'next'}
                                breakLabel={'...'}
                                breakClassName={'break-me'}
                                activeClassName={'active'}
                                initialPage={currentPage}
                                pageCount={pageInfo?.totalPages || 1}
                                marginPagesDisplayed={2}
                                pageRangeDisplayed={5}
                                onPageChange={pagginationHandler}
                            />
                        </div>
                    </main>
                </div>
            </div>
        );
    }
