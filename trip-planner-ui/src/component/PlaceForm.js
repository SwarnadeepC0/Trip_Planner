import React, { Component } from 'react';
import makeAnimated from 'react-select/animated';
import Select from 'react-select';
import axios from 'axios';
const placeTypes = ["restaurant", "museum", "park"];

class PlaceForm extends Component {
    
    typeOptions = [
        { value: 'park', label: 'Park' },
        { value: 'restaurant', label: 'Restaurant' },
        { value: 'museum', label: 'Museum' }
    ]
    animatedComponents = makeAnimated();


  constructor(props) {
    super(props)
    this.state = {
        latitude : '',
        longitude : '',
        placeTypes: [],
        radius: 50,
        data:[]
    }
  }

  handleChangeRadius = (event) => {
    this.setState({
        radius:event.target.value
    })
  }

  handleChangeTypes = (selectOption) => {
    this.setState({
        placeTypes:selectOption
    })
  }

  success = async (position)=>{
    console.log("Fetched location")
    await this.setState({
        latitude : position.coords.latitude,
        longtitude : position.coords.longitude
    }) 
  }

  handleClick = async () => {
    try {
      const position = await new Promise((resolve, reject) => {
        if (navigator.geolocation) {
          navigator.geolocation.getCurrentPosition(resolve, reject);
        } else {
          reject(new Error("Geolocation is not supported by this browser."));
        }
      });
      const latitude = position.coords.latitude;
      const longitude = position.coords.longitude;
      this.setState({ latitude, longitude }, async () => {
        var places = this.state.placeTypes.map(place => place.value);
  
        const response = await axios.post('/manage/hotel/trips/suggestion', {
          latitude: this.state.latitude,
          longtitude: this.state.longitude, 
          filters: places,
          radius: this.state.radius
        });
        var data1 = response.data
        if('places' in data1){
            this.setState({
                data: data1.places
            })
        }else {
            this.setState({
                data: []
            })
        }
      });
  
    } catch (error) {
      console.error("Error fetching location:", error);
    }
  };

  render(){
    return (
        <div>
            <div class="form-container">
                <div class="header"> Trip Planner </div>
                <div class= "form-ele">
                    <label>Places</label>
                    <Select
                        value={this.state.placeTypes}
                        onChange={this.handleChangeTypes}
                        components={this.animatedComponents}
                        closeMenuOnSelect={false}
                        isMulti
                        isClearable
                        options={this.typeOptions}
                    />
                </div>
                <div class="slidecontainer">
                    <label>Radius : {this.state.radius} m</label>
                    <input type="range" min="10" max="500" value={this.state.radius} class="slider" id="myRange" onChange={this.handleChangeRadius}/>
                </div>
                <div>
                    <button class="glow-on-hover" type="button" onClick={this.handleClick}>Submit!</button>
                </div>
            </div>
            <section class="tiles-container">
                {this.state.data.map((place,index)=>{
                    return (<div class="tile" key={index}>
                        <h3 className="font-bold text-lg">{place.name}</h3>
                        <table>
                            <tbody>
                                <tr>
                                    <th id="rating">Rating: {place.rating}</th>
                                    <th id="type">Type: {place.type.toUpperCase()}</th>
                                </tr>
                            </tbody>
                        </table>
                        <p className="mt-2 text-gray-700">{place.summary}</p>
                    </div>)
                })}
            </section>
        </div>
      );
  }
}

export default PlaceForm
