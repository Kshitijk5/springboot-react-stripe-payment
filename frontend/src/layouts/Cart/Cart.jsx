import { useAuth0 } from "@auth0/auth0-react";
import React, { useEffect, useState } from "react";
import CartItem from "./CartItem";
import { toast } from "react-toastify";

export const Cart = () => {
  const { getAccessTokenSilently, isLoading } = useAuth0();
  const [loading, setLoading] = useState(false);
  const [cartItems, setCartItems] = useState();
  const [error, setError] = useState(null);
  const [reloadCart, setReloadCart] = useState(false);

  const getCartItems = async () => {
    setLoading(true);
    const response = await fetch("http://localhost:8080/api/cart", {
      method: "GET",
      headers: {
        Authorization: `Bearer ${await getAccessTokenSilently()}`,
      },
    });
    if (!response.ok) {
      throw new Error("Something went wrong while fetching the cart data!");
    }

    const responseJson = await response.json();
    if (responseJson != null) {
      setCartItems(responseJson);
    }

    setLoading(false);
  };

  //fetch cart items
  useEffect(() => {
    getCartItems().catch((err) => {
      setLoading(false);
      setError(err.message);
    });
  }, [reloadCart]);

  //remove item

  const removeItem = async (courseId) => {
    const updateCartResponse = await fetch(
      `http://localhost:8080/api/cart/course/${courseId}`,
      {
        method: "PUT",
        headers: {
          Authorization: `Bearer ${await getAccessTokenSilently()}`,
        },
      }
    );

    if (!updateCartResponse.ok) {
      toast("Something went wrong while updating the cart!");
      throw new Error("Something went wrong while updating the cart!");
    }

    setReloadCart(!reloadCart);
  };

  return (
    <div className="container mt-5 d-flex justify-content-center align-items-center">
      <div>
        {loading && !cartItems && (
          <div className="spinner-border" role="status">
            <span className="visually-hidden">Loading...</span>
          </div>
        )}
        {!loading && cartItems != null ? (
          <CartItem cartItem={cartItems} removeItem={removeItem} />
        ) : (
          <h2>Your Cart is Empty</h2>
        )}
      </div>
    </div>
  );
};
