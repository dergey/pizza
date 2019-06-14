import React, { Component } from 'react';
import {Button, ButtonGroup, Container, ListGroup, ListGroupItem, Table} from 'reactstrap';
import AppNavbar from '../AppNavbar';
import { Link, withRouter } from 'react-router-dom';
import {authHeader, getAuthorization} from "../helpers/auth-header";
import PageBar from "../PageBar";

class SupplierList extends Component {
  constructor(props) {
    super(props);
    this.state = {
      suppliers: [],
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
    fetch('api/suppliers?page=' + page, {headers: authHeader()})
        .then(response => response.json())
        .then(data => this.setState({suppliers: data.content,  totalPages: data.totalPages, currentPage: data.number, isLoading: false}))
        .catch(() => this.props.history.push('/'));
  }

  async remove(id) {
    await fetch(`/api/suppliers/${id}`, {
      method: 'DELETE',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Authorization': getAuthorization()
      }
    }).then(() => {
      let updatedSuppliers = [...this.state.suppliers].filter(i => i.id !== id);
      this.setState({suppliers: updatedSuppliers});
    });
  }

  render() {
    const {suppliers, currentPage, totalPages, isLoading} = this.state;

    if (isLoading) {
      return <div><AppNavbar/><Container><p>Загрузка...</p></Container></div>;
    }

    const supplierList = suppliers ? suppliers.map(supplier => {
      let address = supplier.address.country + ', ' + supplier.address.address;

      let supplies = supplier.supplies.map(supply => {
        return <ListGroupItem>{supply.ingredient.title + ' за ' + supply.price + ' ' + supply.currency}</ListGroupItem>;
      });

      return <tr key={supplier.id}>
        <td style={{whiteSpace: 'nowrap'}}>{supplier.id}</td>
        <td style={{whiteSpace: 'nowrap'}}>{supplier.title}</td>
        <td style={{whiteSpace: 'nowrap'}}>{address}</td>
        <td className="p-0" style={{whiteSpace: 'nowrap'}}>
          <ListGroup flush>
            {supplies}
          </ListGroup>
        </td>
        <td>
          <ButtonGroup>
            <Button size="sm" color="primary" tag={Link} to={"/suppliers/" + supplier.id}>Изменить</Button>
            <Button size="sm" color="danger" onClick={() => this.remove(supplier.id)}>Удалить</Button>
          </ButtonGroup>
        </td>
      </tr>
    }) : '';

    return (
      <div>
        <AppNavbar/>
        <Container fluid>
          <div className="float-right">
            <Button color="success" tag={Link} to="/suppliers/new">Добавить новый</Button>
          </div>
          <h3>Поставщики</h3>
          <Table className="mt-4">
            <thead>
            <tr>
              <th>Номер</th>
              <th>Название</th>
              <th>Адрес</th>
              <th>Поставляет</th>
              <th>Действия</th>
            </tr>
            </thead>
            <tbody>
            {supplierList}
            </tbody>
          </Table>
          <PageBar currentPage={currentPage} totalPages={totalPages} onPageChange={(e, i) => this.goToPage(i)}/>
        </Container>
      </div>
    );
  }
}

export default withRouter(SupplierList);