import csv
import os

def Convert_To_Csv(data_list, csv_file):
    script_dir = os.path.dirname(os.path.abspath(__file__))
    output_folder_rel = "Data/Csv_Files/"
    output_folder = os.path.join(script_dir, output_folder_rel)
    csv_path = os.path.join(output_folder, csv_file)
    
    # Get the headers dynamically from the keys of the first dictionary in the list
    headers = list(data_list[0].keys())

    # Save the list of dictionaries to the CSV file with quoting set to csv.QUOTE_MINIMAL
    with open(csv_path, "w", newline="", encoding="utf-8") as file:
        writer = csv.DictWriter(file, fieldnames=headers, delimiter=';', quoting=csv.QUOTE_MINIMAL)
        
        # Write the headers
        writer.writeheader()
        
        # Write the data for each page
        for page_data in data_list:
            writer.writerow(page_data)

    print("Data saved to CSV file:", csv_file)


