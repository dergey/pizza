import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavbar from '../AppNavbar';
import { Link, withRouter } from 'react-router-dom';
import {authHeader, getAuthorization} from "../helpers/auth-header";
import PageBar from "../PageBar";

class PizzasList extends Component {
  constructor(props) {
    super(props);
    this.state = {
      pizzas: [],
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
    fetch('api/pizzas?page=' + page, {headers: authHeader()})
        .then(response => response.json())
        .then(data => this.setState({pizzas: data.content,  totalPages: data.totalPages, currentPage: data.number, isLoading: false}))
        .catch(() => this.props.history.push('/'));
  }

  async remove(id) {
    await fetch(`/api/pizzas/${id}`, {
      method: 'DELETE',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Authorization': getAuthorization()
      }
    }).then(() => {
      let updatedIngredients = [...this.state.pizzas].filter(i => i.id !== id);
      this.setState({ingredients: updatedIngredients});
    });
  }

  render() {
    const {pizzas, isLoading, currentPage, totalPages} = this.state;

    if (isLoading) {
      return <div><AppNavbar/><Container><p>Загрузка...</p></Container></div>;
    }

    const pizzaList = pizzas.map(pizza => {
      return<tr key={pizza.id}>
        <td style={{whiteSpace: 'nowrap'}}>{pizza.id}</td>
        <td style={{whiteSpace: 'nowrap'}}>{pizza.title}</td>
        <td style={{whiteSpace: 'nowrap'}}>{pizza.price + ' ' + pizza.currency}</td>
        <td style={{whiteSpace: 'nowrap'}}>{pizza.fat}</td>
        <td style={{whiteSpace: 'nowrap'}}>{pizza.protein}</td>
        <td style={{whiteSpace: 'nowrap'}}>{pizza.carbohydrate}</td>
        <td>
          <ButtonGroup>
            <Button size="sm" color="primary" tag={Link} to={"/pizzas/" + pizza.id}>Изменить</Button>
            <Button size="sm" color="danger" onClick={() => this.remove(pizza.id)}>Удалить</Button>
          </ButtonGroup>
        </td>
      </tr>
    });

    return (
      <div>
        <AppNavbar/>
        <Container fluid>
          <div className="float-right">
            <Button color="success" tag={Link} to="/pizzas/new">Добавить новую</Button>
          </div>
          <h3>Пиццы</h3>
          <Table className="mt-4">
            <thead>
            <tr>
              <th>Номер</th>
              <th>Название</th>
              <th>Цена</th>
              <th>Жиры</th>
              <th>Белки</th>
              <th>Углеводы</th>
              <th>Действия</th>
            </tr>
            </thead>
            <tbody>
            {pizzaList}
            </tbody>
          </Table>
          <PageBar currentPage={currentPage} totalPages={totalPages} onPageChange={(e, i) => this.goToPage(i)}/>
        </Container>
      </div>
    );
  }
}

export default withRouter(PizzasList);