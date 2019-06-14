import React, { Component } from 'react';
import { Link, withRouter } from 'react-router-dom';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
import AppNavbar from '../AppNavbar';
import {authHeader} from "../helpers/auth-header";

class UserEdit extends Component {

  emptyItem = {
    name: '',
    password: '',
    role: []
  };

  constructor(props) {
    super(props);
    this.state = {
      isNew: true,
      item: this.emptyItem,
    };
    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
    this.handleRoleChanged = this.handleRoleChanged.bind(this);
  }

  async componentDidMount() {
    if (this.props.match.params.number !== 'new') {
      try {
        const user = await (await fetch(`/api/users/${this.props.match.params.number}`, {headers: authHeader()})).json();
        this.setState({item: user, isNew: false});
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


  handleRoleChanged(role, enable) {
    let item = {...this.state.item};
    if (enable) {
      item.role.push(role);
    } else {
      item.role.filter(i => i === role);
    }
    this.setState({item});

  }

  async handleSubmit(event) {
    event.preventDefault();
    const {item} = this.state;

    await fetch('/api/users' + (this.state.isNew ? '' : '/' + item.name ), {
      method: (item.id) ? 'PUT' : 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(item),
      credentials: 'include'
    });
    this.props.history.push('/users');
  }

  render() {
    const {item} = this.state;
    const title = <h2>{item.id ? 'Изменить пользователя' : 'Добавить пользователя'}</h2>;

    return <div>
      <AppNavbar/>
      <Container>
        {title}
        <Form onSubmit={this.handleSubmit}>
          <div className="row">
            <FormGroup className="col-md-6 mb-6">
              <Label for="login">Логин</Label>
              <Input type="text" name="name" id="login" value={item.name || ''} onChange={this.handleChange}/>
            </FormGroup>
            <FormGroup className="col-md-6 mb-6">
              <Label for="password">Пароль</Label>
              <Input type="password" name="password" id="password" value={item.password || ''} onChange={this.handleChange}/>
            </FormGroup>
          </div>
          <div className="row">
            <FormGroup className="col-md-6 mb-6">
              <label className="switch">
                <Input type="checkbox" className="default" checked={item.role.includes("ROLE_USER")} onChange={(e) => this.handleRoleChanged( "ROLE_USER", e)}/>
                <span className="slider round"/>
              </label>
            </FormGroup>
            <FormGroup className="col-md-6 mb-6">
              <label className="switch">
                <Input type="checkbox" className="danger" checked={item.role.includes("ROLE_ADMIN")} onChange={(e) => this.handleRoleChanged("ROLE_ADMIN", e)}/>
                <span className="slider round"/>
              </label>
            </FormGroup>
          </div>

          <FormGroup >
            <Button color="primary" type="submit">Сохранить</Button>{' '}
            <Button color="secondary" tag={Link} to="/customers">Отмена</Button>
          </FormGroup>
        </Form>
      </Container>
    </div>
  }
}

export default withRouter(UserEdit);
