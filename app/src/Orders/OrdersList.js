import React, { Component } from 'react';
import {Button, ButtonGroup, Container, ListGroup, ListGroupItem, Table} from 'reactstrap';
import AppNavbar from '../AppNavbar';
import { Link, withRouter } from 'react-router-dom';
import {authHeader, getAuthorization} from "../helpers/auth-header";
import Moment from 'moment';
import PageBar from "../PageBar";
import { saveAs } from 'file-saver';

class OrdersList extends Component {
  constructor(props) {
    super(props);
    this.state = {
      orders: [],
      totalPages: 0,
      currentPage: 0,
      isLoading: true
    };
    this.remove = this.remove.bind(this);
    this.goToPage = this.goToPage.bind(this);
    this.handleDownloadReport = this.handleDownloadReport.bind(this);
  }

  componentDidMount() {
    this.setState({isLoading: true});
    this.goToPage(this.state.currentPage);
  }

  goToPage(page) {
    fetch('api/orders?page=' + page, {headers: authHeader()})
        .then(response => response.json())
        .then(data => this.setState({orders: data.content,  totalPages: data.totalPages, currentPage: data.number, isLoading: false}))
        .catch(() => this.props.history.push('/'));
  }

  handleDownloadReport() {
    fetch("http://localhost:8080/api/report/order", {
      headers: authHeader(),
      credentials: 'include',
      mode: 'no-cors'
    }).then(response => {
          response.blob().then(blob => {
            saveAs(blob, "OrderReport.xls");
          });
        });
  }

  async remove(id) {
    await fetch(`/api/orders/${id}`, {
      method: 'DELETE',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Authorization': getAuthorization()
      }
    }).then(() => {
      let updatedOrders = [...this.state.orders].filter(i => i.id !== id);
      this.setState({orders: updatedOrders});
    });
  }

  render() {
    const {orders, isLoading, currentPage, totalPages} = this.state;

    if (isLoading) {
      return <div><AppNavbar/><Container><p>Загрузка...</p></Container></div>;
    }

    const orderList = orders ? orders.map(order => {
      let fullName = order.customer.firstName + ' ' + order.customer.lastName;

      let orderDate = Moment(new Date(order.orderDate)).format('LLLL');

      let totalPrice = order.totalPrice + ' ' + order.currency;

      let pizzaOrders = order.orders ? order.orders.map(pizzaOrder => {

        let price = (pizzaOrder.count * pizzaOrder.pizza.price) + ' ' + pizzaOrder.pizza.currency;

        return <ListGroupItem>{pizzaOrder.pizza.title + ' x ' + pizzaOrder.count + ' = ' + price}</ListGroupItem>;
      }) : '';

      return <tr key={order.id}>
        <td style={{whiteSpace: 'nowrap'}}>{order.id}</td>
        <td style={{whiteSpace: 'nowrap'}}>{orderDate}</td>
        <td style={{whiteSpace: 'nowrap'}}><Link to={"/customers/" + order.customer.id}>{fullName}</Link></td>
        <td className="p-0" style={{whiteSpace: 'nowrap'}}>
          <ListGroup flush>
            {pizzaOrders}
          </ListGroup>
        </td>
        <td style={{whiteSpace: 'nowrap'}}>{totalPrice}</td>
        <td>
          <ButtonGroup>
            <Button size="sm" color="primary" tag={Link} to={"/orders/" + order.id}>Изменить</Button>
            <Button size="sm" color="danger" onClick={() => this.remove(order.id)}>Удалить</Button>
          </ButtonGroup>
        </td>
      </tr>
    }) : '';

    return (
      <div>
        <AppNavbar/>
        <Container fluid>
          <div className="float-right">
            <Button onClick={this.handleDownloadReport}>Скачать отчет</Button>{' '}
            <Button color="success" tag={Link} to="/orders/new">Добавить новый</Button>
          </div>
          <h3>Заказы</h3>
          <Table className="mt-4">
            <thead>
            <tr>
              <th>Номер</th>
              <th>Время заказа</th>
              <th>Клиент</th>
              <th>Чек</th>
              <th>Итого</th>
              <th>Действия</th>
            </tr>
            </thead>
            <tbody>
            {orderList}
            </tbody>
          </Table>
          <PageBar currentPage={currentPage} totalPages={totalPages} onPageChange={(e, i) => this.goToPage(i)}/>
        </Container>
      </div>
    );
  }
}

export default withRouter(OrdersList);