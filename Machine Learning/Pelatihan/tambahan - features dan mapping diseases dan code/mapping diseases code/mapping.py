import pandas as pd

def save_disease_code_mapping(df):
    # Buat mapping antara kode dan diseases
    disease_mapping = df[['code', 'diseases']].drop_duplicates().reset_index(drop=True)
    disease_mapping.to_csv('diseases_mapping.csv', index=False)
    #validasi total data yang tersimpan
    print(f"Total diseases: {len(disease_mapping)}")
    
    return disease_mapping

#load data
df = pd.read_csv("diseases_ICD_indonesia.csv")

#jalankan function
diseases_df = save_disease_code_mapping(df)