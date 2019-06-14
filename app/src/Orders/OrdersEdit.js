import React, { Component } from 'react';
import { Link, withRouter } from 'react-router-dom';
import Select from 'react-select';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
import AppNavbar from '../AppNavbar';
import {authHeader, getAuthorization} from "../helpers/auth-header";
import DateTimePicker from "react-widgets/lib/DateTimePicker";

class OrdersEdit extends Component {

  emptyOrderItem = {
    orders: [],
    customer: {},
    orderDate: '',
    totalPrice: '',
    currency: ''
  };

  emptyPizzaOrderItem = {
    id: null,
    pizzaId: '',
    count: ''
  };

  constructor(props) {
    super(props);
    this.state = {
      pizzas: [],
      item: this.emptyOrderItem,
      selectedDate: new Date()
    };
    this.remove = this.remove.bind(this);
    this.update = this.update.bind(this);
    this.handleAdd = this.handleAdd.bind(this);
    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
    this.handleDateChange = this.handleDateChange.bind(this);
  }

  async componentDidMount() {
    if (this.props.match.params.number !== 'new') {
      try {
        const order = await (await fetch(`/api/orders/${this.props.match.params.number}`, {headers: authHeader()})).json();
        this.setState({item: order});
        this.setState({selectedDate: new Date(order.orderDate)});
      } catch (error) {
        this.props.history.push('/');
      }
    }

    fetch('/api/pizzas', {headers: authHeader()})
        .then(response => response.json())
        .then(data => {
          this.setState({pizzas: data.content.map(obj => {
              return {value: obj.id, label: obj.title}
            })
          });
        });
  }

  update(pizzaOrder, index) {
      const orders = this.state.item.orders.slice();
      orders[index] = pizzaOrder;
      let item = {...this.state.item};
      item.orders = orders;
      this.setState({item: item});
  }

  remove(id) {
    let updatedOrders = [...this.state.item.orders].filter(i => i.id !== id);
    let item = {...this.state.item};
    item.orders = updatedOrders;
    this.setState({item});
  }

   async handleAdd() {
    const orders = this.state.item.orders.slice();
     orders.push(this.emptyPizzaOrderItem);
    let item = {...this.state.item};
    item.orders = orders;
    this.setState({item: item});
  }

  handleDateChange(date) {
    let item = {...this.state.item};
    item['orderDate'] = date;
    this.setState({item});
    this.setState({ selectedDate: date });
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

    console.log("send : " + JSON.stringify(item));

    await fetch('/api/orders' + (typeof item.id != 'undefined'? '/' + item.id : ''), {
      method: (item.id) ? 'PUT' : 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Authorization': getAuthorization()
      },
      body: JSON.stringify(item)
    });
    this.props.history.push('/orders');
  }

  render() {
    const {item} = this.state;
    const title = <h2>{item.id ? 'Изменить заказ' : 'Добавить заказ'}</h2>;
    return <div>
      <AppNavbar/>
      <Container>
        {title}
        <Form onSubmit={this.handleSubmit}>
          <FormGroup>
            <Label for="fullName">Покупатель</Label>
            <Input type="text" name="fullName" id="fullName" value={item.customer.firstName + ' ' + item.customer.lastName || ''}/>
          </FormGroup>
          <FormGroup>
            <Label for="orderDate">Время заказа</Label>
            <DateTimePicker name="orderDate" id="orderDate" onChange={this.handleDateChange} value={ this.state.selectedDate }/>
           </FormGroup>
           <div className="alert alert-secondary">
              <Label className="btn btn-success" onClick={this.handleAdd}>Добавить</Label>
           </div>
            {item.orders.map((order, index) => {
            return <OrderItemEdit item={order}
                                  pizzas={this.state.pizzas}
                                  removeMethod={this.remove}
                                  updateMethod={(item) => this.update(item, index)}/>
          })}
          <FormGroup className="container">
            <Button color="primary" type="submit">Сохранить</Button>{' '}
            <Button color="secondary" tag={Link} to="/orders">Отмена</Button>
          </FormGroup>
        </Form>
      </Container>
    </div>
  }
}

class OrderItemEdit extends Component {

  constructor(props) {
    super(props);
    this.state = {
      item: props.item,
      selectedItems : {}
    };
    this.handleChange = this.handleChange.bind(this);
    this.handlePizzaChange= this.handlePizzaChange.bind(this);
  }

  async componentDidMount() {
    if (this.props.item.pizza) {
      try {
        this.props.item.pizzaId = this.props.item.pizza.id;
        this.setState({selectedItems: {value: this.props.item.pizza.id, label: this.props.item.pizza.title}});
        delete this.props.item.pizza;
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

  handlePizzaChange(option) {
    let item = {...this.state.item};
    item['pizzaId'] = option.value;
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
            <Label for="ingredient">Пицца</Label>
            <Select name="ingredientId" id="ingredient"
                    value = {this.state.selectedItems}
                    onChange={this.handlePizzaChange}
                    options={this.props.pizzas}/>
          </FormGroup>
          <FormGroup>
            <Label for="count">Количество</Label>
            <Input type="text" name="count" id="count" value={item.count || ''} onChange={this.handleChange}/>
          </FormGroup>
        </Form>
        </div>
      </Container>
    );
  }
}

export default withRouter(OrdersEdit);
