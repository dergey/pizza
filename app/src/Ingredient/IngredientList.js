import React, { Component } from 'react';
import {Button, ButtonGroup, Container, Table} from 'reactstrap';
import AppNavbar from '../AppNavbar';
import PageBar from '../PageBar';
import { Link, withRouter } from 'react-router-dom';
import {authHeader, getAuthorization} from "../helpers/auth-header";

class IngredientList extends Component {
  constructor(props) {
    super(props);
    this.state = {
      ingredients: [],
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
    fetch('api/ingredients?page=' + page, {headers: authHeader()})
        .then(response => response.json())
        .then(data => this.setState({ingredients: data.content,  totalPages: data.totalPages, currentPage: data.number, isLoading: false}))
        .catch(() => this.props.history.push('/'));
  }

  async remove(id) {
    await fetch(`/api/ingredients/${id}`, {
      method: 'DELETE',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Authorization': getAuthorization()
      }
    }).then(() => {
      let updatedIngredients = [...this.state.ingredients].filter(i => i.id !== id);
      this.setState({ingredients: updatedIngredients});
    });
  }

  render() {
    const {ingredients, isLoading, currentPage, totalPages} = this.state;

    if (isLoading) {
      return <div><AppNavbar/><Container><p>Загрузка...</p></Container></div>;
    }

    const ingredientList = ingredients.map(ingredient => {
      return<tr key={ingredient.id}>
        <td style={{whiteSpace: 'nowrap'}}>{ingredient.id}</td>
        <td style={{whiteSpace: 'nowrap'}}>{ingredient.title}</td>
        <td style={{whiteSpace: 'nowrap'}}>{ingredient.shelfLife}</td>
        <td>
          <ButtonGroup>
            <Button size="sm" color="primary" tag={Link} to={"/ingredients/" + ingredient.id}>Изменить</Button>
            <Button size="sm" color="danger" onClick={() => this.remove(ingredient.id)}>Удалить</Button>
          </ButtonGroup>
        </td>
      </tr>
    });

    return (
      <div>
        <AppNavbar/>
        <Container fluid>
          <div className="float-right">
            <Button color="success" tag={Link} to="/ingredients/new">Добавить новый</Button>
          </div>
          <h3>Ингредиенты</h3>
          <Table className="mt-4">
            <thead>
            <tr>
              <th width="20%">Номер</th>
              <th>Название</th>
              <th>Срок годности в часах</th>
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

export default withRouter(IngredientList);