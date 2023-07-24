import { useAuth0 } from "@auth0/auth0-react";
import React from "react";
import { ToastContainer, toast } from "react-toastify";
const CartItem = ({ cartItem, removeItem }) => {
  const { courses, email, total } = cartItem;
  const course = courses[0]; // Assuming there's only one course in the cartItem
  const { getAccessTokenSilently } = useAuth0();
  const handlePayment = async () => {
    try {
      const response = await fetch("http://localhost:8080/api/pay", {
        method: "POST",
        headers: {
          Authorization: `Bearer ${await getAccessTokenSilently()}`,
        },
      });

      const data = await response.json();
      console.log("data-->", data);
      // Redirect to the Stripe payment page
      window.location.href = data.url; // or use window.open() if needed
    } catch (error) {
      toast("Error processing payment");
      console.error("Error processing payment:", error.message);
    }
  };

  return (
    <div className="cart-item card mb-5">
      <div className="card-body">
        <div className="courses d-flex flex-wrap">
          {courses.map((course) => (
            <div key={course.id} className="course card mb-3 flex-grow-1">
              <div className="row g-0">
                <div className="col-md-4">
                  <img
                    src={course.img}
                    alt={course.courseName}
                    className="img-fluid rounded-start course-image"
                  />
                </div>
                <div className="col-md-8">
                  <div className="card-body">
                    <h5 className="card-title">{course.courseName}</h5>
                    <p className="card-text">{course.courseDescription}</p>
                    <p className="card-text">Price: ₹{course.unitPrice}</p>
                    <button
                      className="btn btn-danger"
                      onClick={() => removeItem(course.id)}
                    >
                      Remove
                    </button>
                  </div>
                </div>
              </div>
            </div>
          ))}
        </div>

        <div className="total-wrapper container d-flex justify-content-between">
          <h4 className="card-subtitle mb-2 text-muted">Total: ₹{total}</h4>
          <button
            className="btn btn-primary ml-auto px-4"
            onClick={() => handlePayment()}
          >
            Pay via Stripe
          </button>
        </div>
        <ToastContainer />
      </div>
    </div>
  );
};

export default CartItem;
