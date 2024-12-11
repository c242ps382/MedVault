import pandas as pd

def save_symptoms_features(df):
    #skip diseases code
    symptom_columns = df.columns[2:]
    symptoms_mapping = pd.DataFrame({
        'symptom_name': symptom_columns
    })
    symptoms_mapping.to_csv('symptoms_mapping.csv', index=False)
    #validasi total data
    print(f"Total symptoms: {len(symptoms_mapping)}")
    
    return symptoms_mapping

#load data
df = pd.read_csv("diseases_ICD_indonesia.csv")

#jalankan function
symptoms_df = save_symptoms_features(df)