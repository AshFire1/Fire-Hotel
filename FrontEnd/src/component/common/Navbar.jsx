import React from 'react'
import { NavLink,useNavigate } from 'react-router-dom'
import ApiService from '../../service/ApiService'
const Navbar = () => {
    const isAuthenticated=ApiService.isAuthenticated();
    const isAdmin=ApiService.isAdmin();
    const isUser=ApiService.isUser();
    const navigate=useNavigate();

    const handleLogout=()=>{
        const isLogout=window.confirm("Are you sure you want to logout?");
        if(isLogout){
            ApiService.logout();
        }
        navigate("/login")
    }
  return (
    <nav className='navbar'>
        <div className='navbar-brand'>
            <NavLink to={"/home"}>Fire Hotel</NavLink>

        </div>
        <ul className='navbar-ul'>
            
            <li><NavLink to={"/home"} activeclass="active">Home</NavLink></li>
            <li><NavLink to={"/rooms"} activeclass="active">Rooms</NavLink></li>
            <li><NavLink to={"/find-booking"} activeclass="active">Find My Bookings</NavLink></li>
            {isUser && <li><NavLink to={"/profile"} activeclass="active">Profile</NavLink></li>}
            {isAdmin && <li><NavLink to={"/Admin"} activeclass="active">Admin</NavLink></li>}
            {!isAuthenticated &&<li><NavLink to={"/login"} activeclass="active">Login</NavLink></li>}
            {!isAuthenticated && <li><NavLink to={"/register"} activeclass="active">Register</NavLink></li>}
            {isAuthenticated && <li onClick={handleLogout}>Logout</li>}
          
        </ul>
    </nav>
  )
}

export default Navbar