import { useState, useEffect } from "react";
import { getAllFiles } from "../services/api";

function ImageCarousel() {

    const [images, setImages] = useState([]);

    const [currentIndex, setCurrentIndex] = useState(0);

    const fetchImages = async () => {

        try {

            const response = await getAllFiles();

            setImages(response.data.data);

        } catch (error) {

            console.log(error);
        }
    };

    // AUTO SLIDE
    useEffect(() => {

        if (images.length === 0) return;

        const interval = setInterval(() => {

            setCurrentIndex((prevIndex) =>
                prevIndex === images.length - 1
                    ? 0
                    : prevIndex + 1
            );

        }, 3000);

        return () => clearInterval(interval);

    }, [images]);

    const showPrevious = () => {

        if (images.length === 0) return;

        setCurrentIndex((prevIndex) =>
            prevIndex === 0
                ? images.length - 1
                : prevIndex - 1
        );
    };

    const showNext = () => {

        if (images.length === 0) return;

        setCurrentIndex((prevIndex) =>
            prevIndex === images.length - 1
                ? 0
                : prevIndex + 1
        );
    };

    return (

        <div
            style={{
                textAlign: "center",
                marginTop: "40px"
            }}
        >

            <button
                onClick={fetchImages}
                style={{
                    padding: "10px 20px",
                    fontSize: "16px",
                    cursor: "pointer",
                    borderRadius: "5px"
                }}
            >
                Show Images
            </button>

            <br /><br />

            {
                images.length > 0 && (

                    <div>

                        <h2>
                            Image Carousel
                        </h2>

                        <div
                            style={{
                                display: "flex",
                                justifyContent: "center",
                                alignItems: "center",
                                gap: "20px"
                            }}
                        >

                            <button
                                onClick={showPrevious}
                                style={{
                                    padding: "10px 15px",
                                    cursor: "pointer"
                                }}
                            >
                                ◀ Previous
                            </button>

                            <img
                                src={
                                    images[currentIndex].imageUrl
                                }
                                alt="Uploaded"
                                width="500"
                                height="350"
                                style={{
                                    objectFit: "cover",
                                    borderRadius: "10px",
                                    border: "2px solid gray"
                                }}
                            />

                            <button
                                onClick={showNext}
                                style={{
                                    padding: "10px 15px",
                                    cursor: "pointer"
                                }}
                            >
                                Next ▶
                            </button>

                        </div>

                        <br />

                        <h3>
                            {
                                images[currentIndex]
                                    .originalFileName
                            }
                        </h3>

                    </div>
                )
            }

        </div>
    );
}

export default ImageCarousel;