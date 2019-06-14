import React, { Component } from 'react';
import './App.css';
import Home from './Home';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import IngredientList from './Ingredient/IngredientList';
import IngredientEdit from './Ingredient/IngredientEdit';
import { CookiesProvider } from 'react-cookie';
import {PrivateRoute} from "./components/PrivateRoute";
import {AdminRoute} from "./components/AdminRoute";
import Login from "./Login";
import StockEdit from "./Stock/StockEdit";
import StockList from "./Stock/StockList";
import SupplierList from "./Supplier/SupplierList";
import SupplierEdit from "./Supplier/SupplierEdit";
import PizzasList from "./Pizzas/PizzasList";
import PizzasEdit from "./Pizzas/PizzasEdit";
import CustomersList from "./Customers/CustomersList";
import CustomersEdit from "./Customers/CustomersEdit";
import OrdersList from "./Orders/OrdersList";
import OrdersEdit from "./Orders/OrdersEdit";
import SqlEdit from "./Sql/SqlEdit";
import UserList from "./User/UserList";
import UserEdit from "./User/UserEdit";

class App extends Component {

  render() {
    return (
      <CookiesProvider>
        <Router>
          <Switch>
            <PrivateRoute path='/' exact={true} component={Home}/>
            <Route path="/login" component={Login} />
            <PrivateRoute path='/ingredients' exact={true} component={IngredientList}/>
            <PrivateRoute path='/ingredients/:number' component={IngredientEdit}/>
            <PrivateRoute path='/stock' exact={true} component={StockList}/>
            <PrivateRoute path='/stock/:number' component={StockEdit}/>
            <PrivateRoute path='/suppliers/' exact={true} component={SupplierList}/>
            <PrivateRoute path='/suppliers/:number' component={SupplierEdit}/>
            <PrivateRoute path='/pizzas/' exact={true} component={PizzasList}/>
            <PrivateRoute path='/pizzas/:number' component={PizzasEdit}/>
            <PrivateRoute path='/customers/' exact={true} component={CustomersList}/>
            <PrivateRoute path='/customers/:number' component={CustomersEdit}/>
            <PrivateRoute path='/orders/' exact={true} component={OrdersList}/>
            <PrivateRoute path='/orders/:number' component={OrdersEdit}/>
            <AdminRoute path='/sql' component={SqlEdit}/>
            <AdminRoute path='/users' exact={true} component={UserList}/>
            <AdminRoute path='/users/:number' component={UserEdit}/>
          </Switch>
        </Router>
      </CookiesProvider>
    )
  }
}

export default App;