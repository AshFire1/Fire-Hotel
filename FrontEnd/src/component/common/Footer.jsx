import React from 'react'

const Footer = () => {
  return (
    <footer>
        <span className='my-footer'>
            Fire Hotel | All Rights Reserved &copy;{new Date().getFullYear()}
        </span>
    </footer>
  )
}

export default Footer