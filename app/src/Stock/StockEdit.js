import React, { Component } from 'react';
import { Link, withRouter } from 'react-router-dom';
import Select from 'react-select';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
import AppNavbar from '../AppNavbar';
import {authHeader} from "../helpers/auth-header";
import DateTimePicker from 'react-widgets/lib/DateTimePicker';

class StockEdit extends Component {

  emptyItem = {
    ingredientId: '',
    deliveryDate: '',
    stockType: '',
    count: '',
    weight: '',
  };

  constructor(props) {
    super(props);
    this.state = {
      ingredients: [],
      item: this.emptyItem,
      selectedItems: {},
      selectedStockType: {},
      selectedDate: new Date()
    };
    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
    this.handleDateChange = this.handleDateChange.bind(this);
    this.handleStockTypesChange = this.handleStockTypesChange.bind(this);
    this.handleIngredientChange = this.handleIngredientChange.bind(this);
  }

  async componentDidMount() {
    if (this.props.match.params.number !== 'new') {
      try {
        const stockIngredient = await (await fetch(`/api/stock/${this.props.match.params.number}`, {headers: authHeader()})).json();
        stockIngredient.ingredientId = stockIngredient.ingredient.id;
        this.setState({selectedItems: {value: stockIngredient.ingredient.id, label: stockIngredient.ingredient.title} });
        this.setState({selectedStockType: {
          value: stockIngredient.ingredient.stockType,
          label: stockIngredient.ingredient.stockType === 'PIECE' ? 'Поштучно' : 'Взвешанные'}
        });
        delete stockIngredient.ingredient;
        this.setState({selectedDate: new Date(stockIngredient.deliveryDate) });
        this.setState({item: stockIngredient});
      } catch (error) {
        this.props.history.push('/');
      }
    }

    let item = {...this.state.item};
    item['deliveryDate'] = this.state.selectedDate;
    this.setState({item});

    fetch('/api/ingredients', {headers: authHeader()})
        .then(response => response.json())
        .then(data => {
          this.setState({ingredients: data.content.map(obj => {
              return {value: obj.id, label: obj.title}
            })
          });
        });
  }

  handleChange(event) {
    const target = event.target;
    const value = target.value;
    const name = target.name;
    let item = {...this.state.item};
    item[name] = value;
    this.setState({item});
  }

  handleDateChange(date) {
    let item = {...this.state.item};
    item['deliveryDate'] = date;
    this.setState({item});
    this.setState({ selectedDate: date });
  }

  handleIngredientChange(option) {
    let item = {...this.state.item};
    item['ingredientId'] = option.value;
    this.setState({item});
    this.setState({ selectedItems: {label: option.label, value: option.value} });
    console.log(item);
  }

  handleStockTypesChange(option) {
    let item = {...this.state.item};
    item['stockType'] = option.value;
    if (option.value === 'PIECE') {
      item['weight'] = '';
    } else {
      item['count'] = '';
    }
    this.setState({item});
    this.setState({ selectedStockType: {label: option.label, value: option.value} });
    console.log(item);
  }

  async handleSubmit(event) {
    event.preventDefault();
    const {item} = this.state;

    await fetch('/api/stock' + (typeof item.id != 'undefined'? '/' + item.id : ''), {
      method: (item.id) ? 'PUT' : 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(item),
      credentials: 'include'
    });
    this.props.history.push('/stock');
  }

  render() {
    const {item} = this.state;
    const title = <h2>{item.id ? 'Изменить поставку ингредиента' : 'Добавить поставку ингредиента'}</h2>;
    let stockTypes = [{value: 'PIECE', label: 'Поштучно'}, {value: 'WEIGHED', label: 'Взвешанные'}];
    return <div>
      <AppNavbar/>
      <Container>
        {title}
        <Form onSubmit={this.handleSubmit}>
          <FormGroup>
            <Label for="ingredient">Ингредиент</Label>
            <Select name="ingredientId" id="ingredient"
                    value={ this.state.selectedItems }
                    onChange={this.handleIngredientChange}
                    options={this.state.ingredients}/>
          </FormGroup>
          <FormGroup>
            <Label for="deliveryDate">Время получения</Label>
            <DateTimePicker name="deliveryDate" id="deliveryDate" onChange={this.handleDateChange} value={ this.state.selectedDate }/>
          </FormGroup>
          <FormGroup>
            <Label for="stockType">Единица измерения</Label>
            <Select name="stockType" id="stockType" options={stockTypes} onChange={this.handleStockTypesChange} value={ this.state.selectedStockType }/>
          </FormGroup>
          <div className="row">
            <FormGroup className="col-md-6 mb-3">
              <Label for="weight">Масса</Label>
              <Input type="text" name="weight" id="weight" value={item.weight || ''} onChange={this.handleChange} disabled={this.state.selectedStockType.value === 'PIECE'}/>
            </FormGroup>
            <FormGroup className="col-md-6 mb-3">
               <Label for="count">Количество</Label>
               <Input type="text" name="count" id="count" value={item.count || ''} onChange={this.handleChange} disabled={this.state.selectedStockType.value === 'WEIGHED'}/>
            </FormGroup>
          </div>
          <FormGroup>
            <Button color="primary" type="submit">Сохранить</Button>{' '}
            <Button color="secondary" tag={Link} to="/stock">Отмена</Button>
          </FormGroup>
        </Form>
      </Container>
    </div>
  }
}

export default withRouter(StockEdit);
