import React, { Component } from 'react';
import { Navbar, Nav, NavLink, Dropdown, NavbarBrand, NavbarToggler, Collapse, NavItem, DropdownToggle, DropdownMenu, DropdownItem} from 'reactstrap';
import { Link } from 'react-router-dom';
import {userService} from "./service/user-service";

export default class AppNavbar extends Component {

  constructor(props) {
    super(props);
    this.state = {
      isOpen: false,
      dropdown: false,
      dropdownAdmin: false,
      dropdownPizzas: false,
      dropdownClients: false,
      dropdownIngredients: false
    };

    this.toggle = this.toggle.bind(this);
    this.logout = this.logout.bind(this);
    this.dropdownToggle = this.dropdownToggle.bind(this);
    this.dropdownAdminToggle = this.dropdownAdminToggle.bind(this);
    this.dropdownPizzasToggle = this.dropdownPizzasToggle.bind(this);
    this.dropdownClientsToggle = this.dropdownClientsToggle.bind(this);
    this.dropdownIngredientsToggle = this.dropdownIngredientsToggle.bind(this);
  }

  toggle() {
    this.setState({
      isOpen: !this.state.isOpen
    });
  }

  dropdownToggle() {
    this.setState({
      dropdown: !this.state.dropdown
    });
  }
  dropdownAdminToggle() {
    this.setState({
      dropdownAdmin: !this.state.dropdownAdmin
    });
  }
  dropdownPizzasToggle() {
    this.setState({
      dropdownPizzas: !this.state.dropdownPizzas
    });
  }
  dropdownClientsToggle() {
    this.setState({
      dropdownClients: !this.state.dropdownClients
    });
  }
  dropdownIngredientsToggle() {
    this.setState({
      dropdownIngredients: !this.state.dropdownIngredients
    });
  }

  logout () {
    userService.logout();
    window.location.reload();
  }


  render() {
    const user = JSON.parse(localStorage.getItem('user'));

    return (<Navbar color="light" light expand="md" navbar>
      <NavbarBrand tag={Link} to="/">АРМ Работника пиццерии</NavbarBrand>
      <NavbarToggler onClick={this.toggle}/>
      <Collapse isOpen={this.state.isOpen} navbar>
        <Nav className="mr-auto" navbar>
          <NavItem>
            <NavLink href="/">Главная</NavLink>
          </NavItem>

          <Dropdown isOpen={this.state.dropdownIngredients} toggle={this.dropdownIngredientsToggle}>
            <DropdownToggle nav>Ингредиенты</DropdownToggle>
            <DropdownMenu>
              <DropdownItem href="/ingredients">Список</DropdownItem>
              <DropdownItem href="/stock">Доступные</DropdownItem>
              <DropdownItem href="/suppliers">Поставщики</DropdownItem>
            </DropdownMenu>
          </Dropdown>

          <Dropdown isOpen={this.state.dropdownPizzas} toggle={this.dropdownPizzasToggle}>
            <DropdownToggle nav>Пиццы</DropdownToggle>
            <DropdownMenu>
              <DropdownItem href="/pizzas">Список</DropdownItem>
              <DropdownItem href="/stock">Рецепты</DropdownItem>
            </DropdownMenu>
          </Dropdown>

          <Dropdown isOpen={this.state.dropdownClients} toggle={this.dropdownClientsToggle}>
            <DropdownToggle nav>Клиенты</DropdownToggle>
            <DropdownMenu>
              <DropdownItem href="/customers">Список</DropdownItem>
              <DropdownItem href="/orders">Заказы</DropdownItem>
              <DropdownItem href="/review">Отзывы</DropdownItem>
            </DropdownMenu>
          </Dropdown>

          {user.role.includes("ROLE_ADMIN") &&
          <Dropdown isOpen={this.state.dropdownAdmin} toggle={this.dropdownAdminToggle}>
            <DropdownToggle nav>Администрирование</DropdownToggle>
            <DropdownMenu>
              <DropdownItem href="/sql">SQL редактор</DropdownItem>
              <DropdownItem href="/users">Пользователи</DropdownItem>
            </DropdownMenu>
          </Dropdown>}

        </Nav>
        <Nav>
          <Dropdown isOpen={this.state.dropdown} toggle={this.dropdownToggle}>
            <DropdownToggle color="primary" nav>
              {user.name} <span className="badge badge-danger">{user.role.includes("ROLE_ADMIN") ? 'ADMIN' : 'USER'}</span>
            </DropdownToggle>
            <DropdownMenu right>
              <DropdownItem href="#action/3.1">Ваш профиль</DropdownItem>
              <DropdownItem divider />
              <DropdownItem onClick={this.logout}>Выход</DropdownItem>
            </DropdownMenu>
          </Dropdown>
        </Nav>
      </Collapse>
    </Navbar>);
  }
}