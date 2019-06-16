import React, { Component } from 'react';
import {Button, ButtonGroup, Container, Input, Table} from 'reactstrap';
import AppNavbar from '../AppNavbar';
import { Link, withRouter } from 'react-router-dom';
import {authHeader, getAuthorization} from "../helpers/auth-header";
import PageBar from "../PageBar";

class UserList extends Component {
  constructor(props) {
    super(props);
    this.state = {
      users: [],
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
    fetch('api/users?page=' + page, {headers: authHeader()})
        .then(response => response.json())
        .then(data => this.setState({users: data.content,  totalPages: data.totalPages, currentPage: data.number, isLoading: false}))
        .catch(() => this.props.history.push('/'));
  }

  handleRoleChanged(user, role) {
    console.log(user.role);
    const enable = !user.role.includes(role);
    console.log(role + ' ' + enable);
    if (enable) {
      user.role = user.role.push(role);
    } else {
      user.role = user.role.filter(i => i !== role);
    }
    console.log(user.role);
    fetch(`/api/users/${user.name}`, {
      method: 'PUT',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Authorization': getAuthorization()
      },
      body: JSON.stringify({role: user.role})
    }).then(() => {
      let updatedUsers = [...this.state.users];
      if (enable) {
        updatedUsers.find(userm => userm.name === user.name).role.push(role);
      } else {
        const userm = updatedUsers.find(userm => userm.name === user.name);
        userm.role = userm.role.filter(i => i === role);
      }
      this.setState({users: updatedUsers});
    });
  }

  async remove(name) {
    await fetch(`/api/users/${name}`, {
      method: 'DELETE',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Authorization': getAuthorization()
      }
    }).then(() => {
      let updatedUsers = [...this.state.users].filter(user => user.name !== name);
      this.setState({users: updatedUsers});
    });
  }

  render() {
    const {users, currentPage, totalPages, isLoading} = this.state;

    const yourUser = JSON.parse(localStorage.getItem('user'));

    if (isLoading) {
      return <div><AppNavbar/><Container><p>Загрузка...</p></Container></div>;
    }

    const userList = users ? users.map(user => {
      return <tr key={user.name}>
        <td style={{whiteSpace: 'nowrap'}}>{user.name}</td>
        <td align="left" style={{whiteSpace: 'nowrap'}}>
          <label className="switch">
            <Input type="checkbox" className="default" checked={user.role.includes("ROLE_USER")} onChange={() => this.handleRoleChanged(user, "ROLE_USER")} />
            <span className="slider round"/>
          </label>
        </td>
        <td align="left" style={{whiteSpace: 'nowrap'}}>
          <label className="switch">
            <Input type="checkbox" className="danger" checked={user.role.includes("ROLE_ADMIN")} onChange={() => this.handleRoleChanged(user, "ROLE_ADMIN")}/>
            <span className="slider round"/>
          </label>
        </td>
        <td style={{whiteSpace: 'nowrap'}}>
          {/*<Link to={"/eplouers/" + user..id}>{stockIngredient.ingredient.title}</Link>*/}
        </td>
        <td>
          <ButtonGroup>
            <Button size="sm" color="danger" disabled={user.name === yourUser.name} onClick={() => this.remove(user.name)}>Удалить</Button>
          </ButtonGroup>
        </td>
      </tr>
    }) : '';

    return (
      <div>
        <AppNavbar/>
        <Container fluid>
          <div className="float-right">
            <Button color="success" tag={Link} to="/users/new">Добавить нового</Button>
          </div>
          <h3>Пользователи</h3>
          <Table className="mt-4">
            <thead>
            <tr>
              <th>Имя</th>
              <th>Пользователь</th>
              <th>Администратор</th>
              <th>Профиль</th>
              <th>Действия</th>
            </tr>
            </thead>
            <tbody>
            {userList}
            </tbody>
          </Table>
          <PageBar currentPage={currentPage} totalPages={totalPages} onPageChange={(e, i) => this.goToPage(i)}/>
        </Container>
      </div>
    );
  }

}

export default withRouter(UserList);