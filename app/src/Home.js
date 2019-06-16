import React, {Component} from 'react';
import './App.css';
import AppNavbar from './AppNavbar';
import {Container} from 'reactstrap';
import {withCookies} from 'react-cookie';
import {userService} from "./service/user-service";

class Home extends Component {
    state = {
        isLoading: true,
        user: {},
        table: '',
        error: '',
        sql: ''
    };

    async componentDidMount() {
        this.setState({
            user: JSON.parse(localStorage.getItem('user')),
            users: {loading: true}
        });
        userService.getCurrent().then(user => this.setState({user: user}));
    }


    render() {
        const message = <h2>Добро пожаловать, {this.state.user.name}!</h2>;

        return (
            <div>
                <AppNavbar/>
                <Container fluid>
                    {message}
                </Container>
            </div>
        );
    }
}

export default withCookies(Home);