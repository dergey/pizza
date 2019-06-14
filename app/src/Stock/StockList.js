import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavbar from '../AppNavbar';
import { Link, withRouter } from 'react-router-dom';
import {authHeader, getAuthorization} from "../helpers/auth-header";
import Moment from 'moment';
import PageBar from "../PageBar";

class StockList extends Component {
  constructor(props) {
    super(props);
    this.state = {
      stockIngredients: [],
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
    fetch('api/stock?page=' + page, {headers: authHeader()})
        .then(response => response.json())
        .then(data => this.setState({stockIngredients: data.content,  totalPages: data.totalPages, currentPage: data.number, isLoading: false}))
        .catch(() => this.props.history.push('/'));
  }

  async remove(id) {
    await fetch(`/api/stock/${id}`, {
      method: 'DELETE',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Authorization': getAuthorization()
      }
    }).then(() => {
      let updatedStockIngredients = [...this.state.stockIngredients].filter(i => i.id !== id);
      this.setState({stockIngredients: updatedStockIngredients});
    });
  }

  render() {
    const {stockIngredients, currentPage, totalPages, isLoading} = this.state;

    if (isLoading) {
      return <div><AppNavbar/><Container><p>Загрузка...</p></Container></div>;
    }

    const ingredientList = stockIngredients ? stockIngredients.map(stockIngredient => {
      let deliveryDate = Moment(new Date(stockIngredient.deliveryDate)).format('LLLL');

      return <tr key={stockIngredient.id}>
        <td style={{whiteSpace: 'nowrap'}}>{stockIngredient.id}</td>
        <td style={{whiteSpace: 'nowrap'}}>
          <Link to={"/ingredients/" + stockIngredient.ingredient.id}>{stockIngredient.ingredient.title}</Link>
        </td>
        <td style={{whiteSpace: 'nowrap'}}>{deliveryDate}</td>
        <td style={{whiteSpace: 'nowrap'}}>{stockIngredient.stockType === 'PIECE' ? stockIngredient.count : ''}</td>
        <td style={{whiteSpace: 'nowrap'}}>{stockIngredient.stockType === 'WEIGHED' ? stockIngredient.weight : ''}</td>
        <td>
          <ButtonGroup>
            <Button size="sm" color="primary" tag={Link} to={"/stock/" + stockIngredient.id}>Изменить</Button>
            <Button size="sm" color="danger" onClick={() => this.remove(stockIngredient.id)}>Удалить</Button>
          </ButtonGroup>
        </td>
      </tr>
    }) : '';

    return (
      <div>
        <AppNavbar/>
        <Container fluid>
          <div className="float-right">
            <Button color="success" tag={Link} to="/stock/new">Добавить новый</Button>
          </div>
          <h3>Доступные ингредиенты</h3>
          <Table className="mt-4">
            <thead>
            <tr>
              <th>Номер</th>
              <th>Ингредиент</th>
              <th>Время получения</th>
              <th>Количество</th>
              <th>Вес</th>
              <th>Действия</th>
            </tr>
            </thead>
            <tbody>
            {ingredientList}
            </tbody>
          </Table>
          <PageBar currentPage={currentPage} totalPages={totalPages} onPageChange={(e, i) => this.goToPage(i)}/>
        </Container>
      </div>
    );
  }
}

export default withRouter(StockList);