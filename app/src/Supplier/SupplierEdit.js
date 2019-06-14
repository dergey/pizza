import React, { Component } from 'react';
import { Link, withRouter } from 'react-router-dom';
import Select from 'react-select';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
import AppNavbar from '../AppNavbar';
import {authHeader, getAuthorization} from "../helpers/auth-header";

class SupplierEdit extends Component {

  emptySupplierItem = {
    title: '',
    address: {},
    supplies: []
  };

  emptySupplyItem = {
    id: null,
    ingredientId: '',
    supplyTime: '',
    price: '',
    currency: ''
  };

  constructor(props) {
    super(props);
    this.state = {
      ingredients: [],
      item: this.emptySupplierItem
    };
    this.remove = this.remove.bind(this);
    this.update = this.update.bind(this);
    this.handleAdd = this.handleAdd.bind(this);
    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
    this.handleAddressChange = this.handleAddressChange.bind(this);
  }

  async componentDidMount() {
    if (this.props.match.params.number !== 'new') {
      try {
        const suppliers = await (await fetch(`/api/suppliers/${this.props.match.params.number}`, {headers: authHeader()})).json();
        this.setState({item: suppliers});
      } catch (error) {
        this.props.history.push('/');
      }
    }

    fetch('/api/ingredients', {headers: authHeader()})
        .then(response => response.json())
        .then(data => {
          this.setState({ingredients: data.content.map(obj => {
              return {value: obj.id, label: obj.title}
            })
          });
        });
  }

  update(supply, index) {
      const supplies = this.state.item.supplies.slice();
      supplies[index] = supply;
      let item = {...this.state.item};
      item.supplies = supplies;
      this.setState({item: item});
  }

  remove(id) {
    let updatedSupplies = [...this.state.item.supplies].filter(i => i.id !== id);
    let item = {...this.state.item};
    item.supplies = updatedSupplies;
    this.setState({item});
  }

   async handleAdd() {
    const supplies = this.state.item.supplies.slice();
    supplies.push(this.emptySupplyItem);
    let item = {...this.state.item};
    item.supplies = supplies;
    this.setState({item: item});
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

  async handleSubmit(event) {
    event.preventDefault();
    const {item} = this.state;

    console.log("send : " + JSON.stringify(item));

    await fetch('/api/suppliers' + (typeof item.id != 'undefined'? '/' + item.id : ''), {
      method: (item.id) ? 'PUT' : 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Authorization': getAuthorization()
      },
      body: JSON.stringify(item)
    });
    this.props.history.push('/suppliers');
  }

  render() {
    const {item} = this.state;
    const title = <h2>{item.id ? 'Изменить поставщика' : 'Добавить поставщика'}</h2>;
    return <div>
      <AppNavbar/>
      <Container>
        {title}
        <Form onSubmit={this.handleSubmit}>
          <FormGroup>
            <Label for="title">Название</Label>
            <Input type="text" name="title" id="title" value={item.title || ''} onChange={this.handleChange}/>
          </FormGroup>
          <div className="row">
            <FormGroup className="col-md-4 mb-3">
              <Label for="country">Страна (код)</Label>
              <Input type="text" name="country" id="country" value={item.address ? item.address.country : ''}
                     onChange={this.handleAddressChange}/>
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
          <div className="alert alert-secondary">
              <Label className="btn btn-success" onClick={this.handleAdd}>Добавить</Label>
          </div>
            {item.supplies.map((supply, index) => {
            return <SupplierItemEdit item={supply}
                                     ingredients={this.state.ingredients}
                                     removeMethod={this.remove}
                                     updateMethod={(item) => this.update(item, index)}/>
          })}
          <FormGroup className="container">
            <Button color="primary" type="submit">Сохранить</Button>{' '}
            <Button color="secondary" tag={Link} to="/suppliers">Отмена</Button>
          </FormGroup>
        </Form>
      </Container>
    </div>
  }
}

class SupplierItemEdit extends Component {

  constructor(props) {
    super(props);
    this.state = {
      item: props.item,
      selectedItems : {}
    };
    this.handleChange = this.handleChange.bind(this);
    this.handleIngredientChange = this.handleIngredientChange.bind(this);
  }

  async componentDidMount() {
    if (this.props.item.ingredient) {
      try {
        this.props.item.ingredientId = this.props.item.ingredient.id;
        this.setState({selectedItems: {value: this.props.item.ingredient.id, label: this.props.item.ingredient.title}});
        delete this.props.item.ingredient;
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
    this.props.updateMethod(item);
    this.setState({item});
  }

  handleIngredientChange(option) {
    let item = {...this.state.item};
    item['ingredientId'] = option.value;
    this.setState({item});
    this.props.updateMethod(item);
    this.setState({ selectedItems: {label: option.label, value: option.value} });
  }

  render() {
    const {item} = this.state;

    return (

      <Container className="card px-0">
        <div className="card-header float-right">
          <div className="float-right"><Label className="btn btn-danger" onClick={() => this.props.removeMethod(item.id)}>Удалить</Label></div>
        </div>
        <div className="card-body">
        <Form>
          <FormGroup>
            <Label for="ingredient">Ингредиент</Label>
            <Select name="ingredientId" id="ingredient"
                    value = {this.state.selectedItems}
                    onChange={this.handleIngredientChange}
                    options={this.props.ingredients}/>
          </FormGroup>
          <FormGroup>
            <Label for="supplyTime">Время поставки</Label>
            <Input type="text" name="supplyTime" id="supplyTime" value={item.supplyTime || ''} onChange={this.handleChange}/>
          </FormGroup>
          <div className="row">
            <FormGroup className="col-md-6 mb-6">
              <Label for="price">Цена поставки</Label>
              <Input type="text" name="price" id="price" value={item.price || ''} onChange={this.handleChange}/>
            </FormGroup>
            <FormGroup className="col-md-6 mb-6">
              <Label for="currency">Валюта (код)</Label>
              <Input type="text" name="currency" id="currency" value={item.currency || ''} onChange={this.handleChange}/>
            </FormGroup>
          </div>
        </Form>
        </div>
      </Container>
    );
  }
}

export default withRouter(SupplierEdit);
