import './App.css';
import React from 'react';
import axios from 'axios';


class App extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      weatherForecast: null,  // Will store full API response
      loading: false,
      error: null,
      currentZipCode: '',
    };

    this.searchZipCode = React.createRef('');
    this.getWeatherForecast = this.getWeatherForecast.bind(this);
    this.onFetchWeatherReport = this.onFetchWeatherReport.bind(this);
    this.sortExtendedForecasts = this.sortExtendedForecasts.bind(this);
  }

  sortExtendedForecasts(ef) {
    return ef = [...ef].sort((a, b) => new Date(a.forecastDate) - new Date(b.forecastDate));
  }

  onFetchWeatherReport() {
    this.getWeatherForecast(this.searchZipCode.current.value);
  }

  getWeatherForecast(zipCode) {
    console.log(`Fetching: http://localhost:8080/api/weather/locations/${zipCode}`);

    this.setState({ loading: true, error: null, weatherForecast: null });
    axios.get(`http://localhost:8080/api/weather/locations/${zipCode}`, {
      headers: { "Content-Type": "application/json" }
    })
      .then(response => {
        console.log("Full API Response:", response.data);

        // Ensure response data is an object before updating state
        this.setState({ weatherForecast: response.data || {}, 
          loading: false, error: null, 
          currentZipCode: this.searchZipCode.current.value }, () => {
          console.log("Updated State:", this.state.weatherForecast);
        });
      })
      .catch(err => {
        console.error("API Error:", err);
        this.setState({
          error: "Failed to fetch weather data. ZIP code does not exist!",
          loading: false, weatherForecast: null,
          currentZipCode: this.searchZipCode.current.value
        });
      });
  }

  render() {
    const { weatherForecast, loading, error, currentZipCode } = this.state;
    let content;

    if (!weatherForecast && !loading && !error) {
      content = <p>Please enter a ZIP code to fetch the weather report.</p>;
    } else if (loading) {
      content = <p>Loading... </p>;
    } else if (error) {
      content = <p style={{ color: "red" }}>{error}</p>;
    } else if (weatherForecast && Array.isArray(weatherForecast.extendedForecasts) &&
      weatherForecast.extendedForecasts.length > 0) {
      weatherForecast.extendedForecasts = this.sortExtendedForecasts(weatherForecast.extendedForecasts);
      content = (
        <div>
          <label>Current Temperature: </label> {weatherForecast.dailyWeather[0].currentTemperature}
          <br />
          <br />
          <table border='1px' style={{ width: "90%" }}>
            <thead>
              <tr className='table-header'>
                <th align='left'>Index</th>
                <th align='left'>Date</th>
                <th align='left'>Low Temperature (F)</th>
                <th align='left'>High Temperature (F)</th>
                <th align='left'>Condition</th>
                <th align='left'>Precipitation Chance</th>
                <th align='left'>Wind Speed (mph)</th>
                <th align='left'>Humidity</th>
              </tr>
            </thead>
            <tbody>
              {weatherForecast.extendedForecasts.map((extendedForecast, index) => (
                <tr key={extendedForecast.forecastId}>
                  <td align="left">{index + 1}</td>
                  <td align="left">{extendedForecast.forecastDate}</td>
                  <td align="left">{extendedForecast.lowTemperature}</td>
                  <td align="left">{extendedForecast.highTemperature}</td>
                  <td align="left">{extendedForecast.conditions}</td>
                  <td align="left">{extendedForecast.precipitationChance}%</td>
                  <td align="left">{extendedForecast.windSpeed}</td>
                  <td align="left">{extendedForecast.humidity}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      );
    } else {
      content = <p>No weather forecast data available.</p>;
    }

    return (
      <div className="App" style={{ textAlign: "left", paddingLeft: "20px", paddingRight: "20px" }}>
        <br />
        <form>
          <label>Please enter ZIP code</label>{" "}
          <input
            type="text"
            ref={this.searchZipCode}
          />
          {" "}
          <button type="button" onClick={this.onFetchWeatherReport}>Fetch Weather Report</button>
          <br /><br />
          <label>ZIP Code:</label>  {currentZipCode}
          <div style={{ marginTop: "20px" }}>
            {content}
          </div>
        </form>
      </div>
    );
  }
}

export default App;
