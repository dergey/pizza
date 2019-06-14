import React, { Component } from 'react';
import { Link, withRouter } from 'react-router-dom';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
import AppNavbar from '../AppNavbar';
import {authHeader} from "../helpers/auth-header";
import Select from "react-select";
import {countries, getCountry} from "../helpers/countries";

class CustomersEdit extends Component {

  emptyItem = {
    firstName: '',
    lastName: '',
    address: {}
  };

  constructor(props) {
    super(props);
    this.state = {
      item: this.emptyItem,
      selectedCountry: {}
    };
    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
    this.handleCountryChange = this.handleCountryChange.bind(this);
    this.handleAddressChange = this.handleAddressChange.bind(this);
  }

  async componentDidMount() {
    if (this.props.match.params.number !== 'new') {
      try {
        const customer = await (await fetch(`/api/customers/${this.props.match.params.number}`, {headers: authHeader()})).json();
        this.setState({item: customer, selectedCountry: {label: getCountry(customer.address.country), value: customer.address.country}});
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

  handleAddressChange(event) {
    const target = event.target;
    const value = target.value;
    const name = target.name;
    let item = {...this.state.item};
    item.address[name] = value;
    this.setState({item: item});
  }

  handleCountryChange(event) {
    let item = {...this.state.item};
    item.address.country = event.value;
    this.setState({item: item, selectedCountry: event});
  }

  async handleSubmit(event) {
    event.preventDefault();
    const {item} = this.state;

    await fetch('/api/customers' + (typeof item.id != 'undefined'? '/' + item.id : ''), {
      method: (item.id) ? 'PUT' : 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(item),
      credentials: 'include'
    });
    this.props.history.push('/customers');
  }

  render() {
    const {item, selectedCountry} = this.state;
    const title = <h2>{item.id ? 'Изменить покупателя' : 'Добавить покупателя'}</h2>;

    return <div>
      <AppNavbar/>
      <Container>
        {title}
        <Form onSubmit={this.handleSubmit}>
          <div className="row">
            <FormGroup className="col-md-6 mb-6">
              <Label for="firstName">Имя</Label>
              <Input type="text" name="firstName" id="firstName" value={item.firstName || ''} onChange={this.handleChange}/>
            </FormGroup>
            <FormGroup className="col-md-6 mb-6">
              <Label for="lastName">Фамилия</Label>
              <Input type="text" name="lastName" id="lastName" value={item.lastName || ''} onChange={this.handleChange}/>
            </FormGroup>
          </div>
          <div className="row">
            <FormGroup className="col-md-4 mb-3">
              <Label for="country">Страна</Label>
              <Select isSearchable onChange={this.handleCountryChange} value={selectedCountry} options={countries}/>
            </FormGroup>
            <FormGroup className="col-md-5 mb-3">
              <Label for="country">Адрес</Label>
              <Input type="text" name="address" id="address" value={item.address ? item.address.address : ''}
                     onChange={this.handleAddressChange}/>
            </FormGroup>
            <FormGroup className="col-md-3 mb-3">
              <Label for="country">Почтовый индекс</Label>
              <Input type="text" name="zipCode" id="zipCode" value={item.address ? item.address.zipCode : ''}
                     onChange={this.handleAddressChange}/>
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

export default withRouter(CustomersEdit);
