import React, { Component } from 'react';
import axios from 'axios';

class PaymentComponent extends Component {

    constructor(props){
        super(props)
        this.state={
            link: ''
        }
    }

    handleClick = async (event)=>{
        const response = await axios.post('/manage/hotels/1234/rooms/5678', {
            "roomId":"5678",
            "hotelId":"1234",
            "customerId": "abcd",
            "days":4,
            "person":2
        });
        console.log(response.data)
        var link=response.data
        window.open(link, '_blank', 'noopener,noreferrer');
    }

    render(){
        return (
            <div>
                <div >
                    <button class="btn" type="button" onClick={this.handleClick}><b>Support Me!</b></button>
                </div>
                <div class="copyright">
                    <b>© Copyright 2025 Yuroichi</b>
                </div>
            </div>
        )}
}
export default PaymentComponent