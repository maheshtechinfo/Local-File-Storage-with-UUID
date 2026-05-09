import axios from "axios";

const API_BASE_URL = "http://localhost:8081/api/files";

export const uploadFile = async (formData) => {
    return await axios.post(
        `${API_BASE_URL}/upload`,
        formData,
        {
            headers: {
                "Content-Type": "multipart/form-data",
            },
        }
    );
};

export const getAllFiles = async () => {
    return await axios.get(API_BASE_URL);
};