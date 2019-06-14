import React, { Component } from 'react';
import { Link, withRouter } from 'react-router-dom';
import {Button, Container, Form, FormGroup, Input, Label, Table} from 'reactstrap';
import AppNavbar from '../AppNavbar';
import {authHeader, getAuthorization} from "../helpers/auth-header";

class SqlEdit extends Component {

  constructor(props) {
    super(props);
    this.state = {
      isLoading: true,
      user: {},
      table: '',
      error: '',
      sql: ''
    };
    this.handleSubmit = this.handleSubmit.bind(this);
    this.handleChange = this.handleChange.bind(this);
    this.handleResponse = this.handleResponse.bind(this);
  }

  handleChange(event) {
    const target = event.target;
    const value = target.value;
    this.setState({sql: value});
  }

  async handleSubmit(event) {
    event.preventDefault();
    const {item} = this.state;

    this.setState({isLoading: true});

    fetch('/api/sql', {
      method: 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Authorization': getAuthorization()
      },
      body: JSON.stringify({sql: this.state.sql})
    }).then(this.handleResponse)
        .then(data => this.setState({table: data, error: '', isLoading: false}),
            error => this.setState({table: '', error: error, isLoading: false}));
  }

  async handleResponse(response) {
    return response.text().then(text => {
      const data = text && JSON.parse(text);
      if (!response.ok) {
        const error = (data && data.message) || response.code;
        return Promise.reject(error);
      }

      return data;
    });
  }

  render() {
    const {isLoading, table, error, sql} = this.state;

    const sqlTableHeader = table.columns ? table.columns.map(column => {
      return <td>{column}</td>;
    }) : '';

    const sqlTableContent = table.rows ? table.rows.map(row => {
      const tableItems = row.items.map(item => {
        return <td style={{whiteSpace: 'nowrap'}}>{item}</td>;
      });
      return (<tr key={row.index}>{tableItems}</tr>);
    }) : '';

    const errorAlert = error ? <div className="alert alert-danger">{error}</div> : '';

    return (
        <div>
          <AppNavbar/>
          <Container>
            <h2>SQL редактор</h2>

            <Form onSubmit={this.handleSubmit}>
              <Container className="row">
                <FormGroup className="col-9">
                  <Input type="text" name="sql" id="sql" value={sql} onChange={this.handleChange}/>
                </FormGroup>
                <FormGroup className="col-3">
                  <Button color="primary" type="submit">Отправить</Button>{' '}
                </FormGroup>
              </Container>
              <Container className="col-9">
                {errorAlert}
              </Container>
            </Form>
            <Table className="mt-4">
              <thead>
              <tr>
                {sqlTableHeader}
              </tr>
              </thead>
              <tbody>
              {sqlTableContent}
              </tbody>
            </Table>
          </Container>
        </div>
    );

  }
}

export default withRouter(SqlEdit);
