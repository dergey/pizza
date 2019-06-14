import React from 'react';
import { Route, Redirect } from 'react-router-dom';

export const AdminRoute = ({ component: Component, ...rest }) => (
    <Route {...rest} render={props => {
        const user = JSON.parse(localStorage.getItem('user'));
        if (!user || !user.role) {
            return (<Redirect to={{ pathname: '/login', state: { from: props.location } }} />);
        }
        if (!user.role.includes("ROLE_ADMIN")) {
            return (<Redirect to={{ pathname: '/', state: { from: props.location } }} />);
        }
        return <Component {...props} />;
    }}/>
);