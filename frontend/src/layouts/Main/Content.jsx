import React, { useEffect, useState } from "react";
import { useAuth0 } from "@auth0/auth0-react";
import { Card } from "./Card";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

export const Content = () => {
  const { user, getAccessTokenSilently, isAuthenticated, isLoading } =
    useAuth0();
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [courses, setCourses] = useState();

  if (isAuthenticated) {
    const getToken = async () => {
      const token = await getAccessTokenSilently();
      console.log(token);
    };
    getToken();
  }

  const fetchCourses = async () => {
    setLoading(true);
    const response = await fetch("http://localhost:8080/api/courses");
    if (!response.ok) {
      throw new Error("Something went wrong while fetching the data!");
    }

    const responseJson = await response.json();
    setCourses(responseJson);

    setLoading(false);
  };

  useEffect(() => {
    fetchCourses().catch((err) => {
      setLoading(false);
      setError(err.message);
    });
  }, []);

  //add to cart functionality

  const addToCart = async (courseId) => {
    const checkIfBought = await fetch(
      `http://localhost:8080/api/courses/user/${courseId}`,
      {
        method: "GET",
        headers: {
          Authorization: `Bearer ${await getAccessTokenSilently()}`,
        },
      }
    );
    if (checkIfBought.status == 200) {
      const addToCartResponse = await fetch(
        `http://localhost:8080/api/cart/course/${courseId}`,
        {
          method: "POST",
          headers: {
            Authorization: `Bearer ${await getAccessTokenSilently()}`,
          },
        }
      );

      if (!addToCartResponse.ok) {
        toast("Something went wrong while adding to cart");
        throw new Error(
          "Something went wrong while adding the course to the cart"
        );
      }
      if (addToCartResponse.status === 200) {
        toast(`Added to cart!`);
      }
    }
    if (checkIfBought.status == 400) {
      toast("Course Already bought check your email!");
    }
  };

  return (
    <div
      style={{ minHeight: "90vh" }}
      className="container d-flex justify-content-center align-items-center"
    >
      <div className="container mt-2 d-flex justify-content-center align-items-center gap-3 ">
        {courses &&
          courses.map((course) => {
            return <Card key={course.id} course={course} adder={addToCart} />;
          })}

        {loading && (
          <div className="spinner-border" role="status">
            <span className="visually-hidden">Loading...</span>
          </div>
        )}
        {error && <div>{error}</div>}
      </div>
      <ToastContainer />
    </div>
  );
};
