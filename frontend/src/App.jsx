import { Cart } from "./layouts/Cart/Cart";
import { Navbar } from "./layouts/Header/Navbar";
import { Content } from "./layouts/Main/Content";
import {
  BrowserRouter as Router,
  Switch,
  Route,
  Redirect,
} from "react-router-dom";
import { Success } from "./layouts/Main/Success";
import { Error } from "./layouts/Main/Error";
function App() {
  return (
    <div>
      <Router>
        <Navbar />
        <Switch>
          <Route component={Content} exact path={"/"}></Route>
          <Route path={"/home"}>
            <Redirect to={"/"}></Redirect>
          </Route>
          <Route component={Cart} path={"/cart"}></Route>

          <Route path={"/success"} component={Success}></Route>
          <Route path={"/error"} component={Error}></Route>
        </Switch>
      </Router>
    </div>
  );
}

export default App;
