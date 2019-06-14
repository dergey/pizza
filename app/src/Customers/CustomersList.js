import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavbar from '../AppNavbar';
import { getCountry } from '../helpers/countries';
import { Link, withRouter } from 'react-router-dom';
import {authHeader, getAuthorization} from "../helpers/auth-header";
import PageBar from "../PageBar";

class CustomersList extends Component {
  constructor(props) {
    super(props);
    this.state = {
      customers: [],
      totalPages: 0,
      currentPage: 0,
      isLoading: true
    };
    this.remove = this.remove.bind(this);
    this.goToPage = this.goToPage.bind(this);
  }

  componentDidMount() {
    this.setState({isLoading: true});
    this.goToPage(this.state.currentPage);
  }

  goToPage(page) {
    fetch('api/customers?page=' + page, {headers: authHeader()})
        .then(response => response.json())
        .then(data => this.setState({customers: data.content,  totalPages: data.totalPages, currentPage: data.number, isLoading: false}))
        .catch(() => this.props.history.push('/'));
  }

  async remove(id) {
    await fetch(`/api/customers/${id}`, {
      method: 'DELETE',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Authorization': getAuthorization()
      }
    }).then(() => {
      let updatedCustomers = [...this.state.customers].filter(i => i.id !== id);
      this.setState({customers: updatedCustomers});
    });
  }

  render() {
    const {customers, isLoading, currentPage, totalPages} = this.state;

    if (isLoading) {
      return <div><AppNavbar/><Container><p>Загрузка...</p></Container></div>;
    }

    const customerList = customers.map(customer => {
      return<tr key={customer.id}>
        <td style={{whiteSpace: 'nowrap'}}>{customer.id}</td>
        <td style={{whiteSpace: 'nowrap'}}>{customer.firstName + ' ' + customer.lastName}</td>
        <td style={{whiteSpace: 'nowrap'}}>{getCountry(customer.address.country) + ', ' + customer.address.address}</td>
        <td>
          <ButtonGroup>
            <Button size="sm" color="primary" tag={Link} to={"/customers/" + customer.id}>Изменить</Button>
            <Button size="sm" color="danger" onClick={() => this.remove(customer.id)}>Удалить</Button>
          </ButtonGroup>
        </td>
      </tr>
    });

    return (
      <div>
        <AppNavbar/>
        <Container fluid>
          <div className="float-right">
            <Button color="success" tag={Link} to="/customers/new">Добавить нового</Button>
          </div>
          <h3>Покупатели</h3>
          <Table className="mt-4">
            <thead>
            <tr>
              <th>Номер</th>
              <th>Имя</th>
              <th>Адрес</th>
              <th>Действия</th>
            </tr>
            </thead>
            <tbody>
            {customerList}
            </tbody>
          </Table>
          <PageBar currentPage={currentPage} totalPages={totalPages} onPageChange={(e, i) => this.goToPage(i)}/>
        </Container>
      </div>
    );
  }
}

export default withRouter(CustomersList);