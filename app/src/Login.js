import React, {Component} from 'react';
import './Login.css';
import {userService} from "./service/user-service";

class Login extends Component {

    constructor(props) {
        super(props);

        userService.logout();

        this.state = {
            username: '',
            password: '',
            submitted: false,
            isLoading: false,
            error: ''
        };

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleChange(e) {
        const {name, value} = e.target;
        this.setState({[name]: value});
    }

    handleSubmit(e) {
        e.preventDefault();

        this.setState({submitted: true});
        const {username, password, returnUrl} = this.state;

        // stop here if form is invalid
        if (!(username && password)) {
            return;
        }

        this.setState({loading: true});
        userService.login(username, password)
            .then(
                user => {
                    const {from} = this.props.location.state || {from: {pathname: "/"}};
                    this.props.history.push(from);
                },
                error => this.setState({error, loading: false})
            );
    }

    render() {
        const {username, password, submitted, isLoading, error} = this.state;
        return (
            <div className="login-container">
                <div className="login-content">
                    <h1 className="login-title">Войти в систему</h1>
                    <form name="form" onSubmit={this.handleSubmit}>
                        <div className={'form-item' + (submitted && !username ? ' has-error' : '')}>
                            <input type="text" className="form-control" name="username" value={username}
                                   placeholder="Логин" onChange={this.handleChange}/>
                            {submitted && !username &&
                            <div className="help-block">Введите логин!</div>
                            }
                        </div>
                        <div className={'form-item' + (submitted && !password ? ' has-error' : '')}>
                            <input type="password" className="form-control" name="password" value={password}
                                   placeholder="Пароль" onChange={this.handleChange}/>
                            {submitted && !password &&
                            <div className="help-block">Введите пароль!</div>
                            }
                        </div>
                        {error &&
                        <div className={'alert alert-danger'}>{error}</div>
                        }
                        <div className="form-item">
                            <button type="submit" className="btn btn-block btn-primary" disabled={isLoading}>Войти
                            </button>
                        </div>

                    </form>
                </div>
            </div>
        );
    }
}

export default Login