import {Pagination, PaginationItem, PaginationLink} from "reactstrap";
import React, {Component} from "react";

export default class  PageBar extends Component {

    constructor(props) {
        super(props);
    }

    render() {
        return (<div className="pagination-wrapper">
            <Pagination aria-label="Page navigation">
                <PaginationItem disabled={this.props.currentPage <= 0}>
                    <PaginationLink onClick={e => this.props.onPageChange(e, this.props.currentPage - 1)} previous href="#"/>
                </PaginationItem>
                {[...Array(this.props.totalPages)].map((page, i) =>
                    <PaginationItem active={i === this.props.currentPage} key={i}>
                        <PaginationLink onClick={e => this.props.onPageChange(e, i)} href="#">
                            {i + 1}
                        </PaginationLink>
                    </PaginationItem>
                )}
                <PaginationItem disabled={this.props.currentPage >= this.props.totalPages - 1}>
                    <PaginationLink onClick={e => this.props.onPageChange(e, this.props.currentPage + 1)} next href="#"/>
                </PaginationItem>
            </Pagination>
        </div>)
}

}
