import React, { Component } from 'react';
import { Link, withRouter } from 'react-router-dom';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
import AppNavbar from '../AppNavbar';
import {authHeader} from "../helpers/auth-header";

class PizzasEdit extends Component {

  emptyItem = {
    title: '',
    price: '',
    currency: '',
    fat: '',
    protein: '',
    carbohydrate: ''
  };

  constructor(props) {
    super(props);
    this.state = {
      item: this.emptyItem
    };
    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  async componentDidMount() {
    if (this.props.match.params.number !== 'new') {
      try {
        const pizza = await (await fetch(`/api/pizzas/${this.props.match.params.number}`, {headers: authHeader()})).json();
        this.setState({item: pizza});
      } catch (error) {
        this.props.history.push('/');
      }
    }
  }

  handleChange(event) {
    const target = event.target;
    const value = target.value;
    const name = target.name;
    let item = {...this.state.item};
    item[name] = value;
    this.setState({item});
  }

  async handleSubmit(event) {
    event.preventDefault();
    const {item} = this.state;

    await fetch('/api/pizzas' + (typeof item.id != 'undefined'? '/' + item.id : ''), {
      method: (item.id) ? 'PUT' : 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(item),
      credentials: 'include'
    });
    this.props.history.push('/pizzas');
  }

  render() {
    const {item} = this.state;
    const title = <h2>{item.id ? 'Изменить пиццу' : 'Добавить пиццу'}</h2>;

    return <div>
      <AppNavbar/>
      <Container>
        {title}
        <Form onSubmit={this.handleSubmit}>
          <FormGroup>
            <Label for="title">Название</Label>
            <Input type="text" name="title" id="title" value={item.title || ''}
                   onChange={this.handleChange}/>
          </FormGroup>
          <div className="row">
            <FormGroup className="col-md-6 mb-6">
              <Label for="price">Цена пиццы</Label>
              <Input type="text" name="price" id="price" value={item.price || ''} onChange={this.handleChange}/>
            </FormGroup>
            <FormGroup className="col-md-6 mb-6">
              <Label for="currency">Валюта (код)</Label>
              <Input type="text" name="currency" id="currency" value={item.currency || ''} onChange={this.handleChange}/>
            </FormGroup>
          </div>
          <div className="row">
            <FormGroup className="col-md-4 mb-4">
              <Label for="fat">Жиры</Label>
              <Input type="text" name="fat" id="fat" value={item.fat || ''} onChange={this.handleChange}/>
            </FormGroup>
            <FormGroup className="col-md-4 mb-4">
              <Label for="protein">Белки</Label>
              <Input type="text" name="protein" id="protein" value={item.protein || ''} onChange={this.handleChange}/>
            </FormGroup>
            <FormGroup className="col-md-4 mb-4">
              <Label for="carbohydrate">Углеводы</Label>
              <Input type="text" name="carbohydrate" id="carbohydrate" value={item.carbohydrate || ''} onChange={this.handleChange}/>
            </FormGroup>
          </div>

          <FormGroup >
            <Button color="primary" type="submit">Сохранить</Button>{' '}
            <Button color="secondary" tag={Link} to="/pizzas">Отмена</Button>
          </FormGroup>
        </Form>
      </Container>
    </div>
  }
}

export default withRouter(PizzasEdit);
