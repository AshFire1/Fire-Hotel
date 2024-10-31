import './App.css';
import React from 'react';
import { BrowserRouter,Routes,Route,Navigate } from 'react-router-dom';
import Navbar from './component/common/Navbar';
import Footer from './component/common/Footer';
import HomePage from './component/home/Homepage';
import AllRoomsPage from './component/booking_rooms/All_Rooms_Page';
import FindBookingPage from './component/booking_rooms/FindBookingPage';
import RoomDetailsPage from './component/booking_rooms/RoomDetailsPage';
import LoginPage from './component/auth/LoginPage';
import RegisterPage from './component/auth/RegisterPage';
import ProfilePage from './component/profile/ProfilePage';
import EditProfilePage from './component/profile/EditProfilePage';
import { ProtectedRoute,AdminRoute } from './service/guard';
import AdminPage from './component/admin/AdminPage';
import ManageBookingsPage from './component/admin/ManageBookingsPage';
import ManageRoomPage from './component/admin/ManageRoomPage';
import AddRoomPage from './component/admin/AddNewRoom';
function App() {
  return (
    <BrowserRouter>
      <div className="App">
        <Navbar/>
        <div className='content'>
          <Routes>
            {/* Public Routes */}
            <Route path='*'element={<Navigate to={"/home"}/>}/>
          <Route exact path='/home' element={<HomePage/>}/>
          <Route exact path='/rooms' element={<AllRoomsPage/>}/>
          <Route exact path='/find-booking' element={<FindBookingPage/>}/>
          <Route exact path='/login' element={<LoginPage/>}/>
          <Route exact path='/register' element={<RegisterPage/>}/>
          {/* Protected Routes */}
          <Route exact path='/room-details-book/:roomId' element={<ProtectedRoute element={<RoomDetailsPage/>}/>}/>
          <Route exact path='/profile' element={<ProtectedRoute element={<ProfilePage/>}/>}/>
          <Route exact path='/edit-profile' element={<ProtectedRoute element={<EditProfilePage/>}/>}/>
          {/* Admin Routes */}
          
          <Route exact path='/admin' element={<AdminRoute element={<AdminPage/>}/>}/>
          <Route exact path='/admin/manage-rooms' element={<AdminRoute element={<ManageRoomPage/>}/>}/>
          <Route exact path='/admin/manage-bookings' element={<AdminRoute element={<ManageBookingsPage/>}/>}/>
          <Route exact path='/admin/add-room' element={<AdminRoute element={<AddRoomPage/>}/>}/>
          <Route exact path='/admin/edit-room/:roomId' element={<AdminRoute element={<EditProfilePage/>}/>}/>
          <Route exact path='/admin/edit-booking/:bookingCode' element={<AdminRoute element={<EditProfilePage/>}/>}/>







          <Route path='*'element={<Navigate to={"/home"}/>}/>
          </Routes>

        </div>
        
      </div>
      <Footer/>
    </BrowserRouter>
   
  );
}

export default App;
