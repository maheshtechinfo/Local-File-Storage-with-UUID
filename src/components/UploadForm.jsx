import { useState } from "react";
import { uploadFile } from "../services/api";

function UploadForm() {

    const [selectedFile, setSelectedFile] = useState(null);

    const [message, setMessage] = useState("");

    const handleFileChange = (event) => {

        setSelectedFile(event.target.files[0]);
    };

    const handleUpload = async () => {

        if (!selectedFile) {

            alert("Please select file");

            return;
        }

        const formData = new FormData();

        formData.append("file", selectedFile);

        try {

            const response =
                await uploadFile(formData);

            setMessage(response.data.message);

            alert("File uploaded successfully");

        } catch (error) {

            console.log(error);

            alert("Upload failed");
        }
    };

    return (
        <div style={{ textAlign: "center" }}>

            <h1>Image Upload</h1>

            <input
                type="file"
                accept=".jpg,.jpeg,.png"
                onChange={handleFileChange}
            />

            <br /><br />

            {
                selectedFile &&
                <p>
                    Selected File:
                    {selectedFile.name}
                </p>
            }

            <button onClick={handleUpload}>
                Submit
            </button>

            <p>{message}</p>

        </div>
    );
}

export default UploadForm;