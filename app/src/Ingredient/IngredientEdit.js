import React, { Component } from 'react';
import { Link, withRouter } from 'react-router-dom';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
import AppNavbar from '../AppNavbar';
import { instanceOf } from 'prop-types';
import { Cookies, withCookies } from 'react-cookie';
import {authHeader} from "../helpers/auth-header";

class IngredientEdit extends Component {
  static propTypes = {
    cookies: instanceOf(Cookies).isRequired
  };

  emptyItem = {
    title: '',
    shelfLife: '',
  };

  constructor(props) {
    super(props);
    const {cookies} = props;
    this.state = {
      item: this.emptyItem
    };
    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  async componentDidMount() {
    if (this.props.match.params.number !== 'new') {
      try {
        const ingredient = await (await fetch(`/api/ingredients/${this.props.match.params.number}`, {headers: authHeader()})).json();
        this.setState({item: ingredient});
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

    await fetch('/api/ingredients' + (typeof item.id != 'undefined'? '/' + item.id : ''), {
      method: (item.id) ? 'PUT' : 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(item),
      credentials: 'include'
    });
    this.props.history.push('/ingredients');
  }

  render() {
    const {item} = this.state;
    const title = <h2>{item.id ? 'Изменить ингредиент' : 'Добавить ингредиент'}</h2>;

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
          <FormGroup>
            <Label for="shelfLife">Срок годности в часах</Label>
            <Input type="text" name="shelfLife" id="shelfLife" value={item.shelfLife || ''}
                   onChange={this.handleChange}/>
          </FormGroup>
          <FormGroup>
            <Button color="primary" type="submit">Сохранить</Button>{' '}
            <Button color="secondary" tag={Link} to="/ingredients">Отмена</Button>
          </FormGroup>
        </Form>
      </Container>
    </div>
  }
}

export default withCookies(withRouter(IngredientEdit));
