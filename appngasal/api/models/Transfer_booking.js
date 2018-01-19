/**
 * Transfer_booking.js
 *
 * @description :: TODO: You might write a short summary of how this model works and what it represents here.
 * @docs        :: http://sailsjs.org/documentation/concepts/models-and-orm/models
 */

module.exports = {

  attributes: {
    booking_id:{
      type:'string'
    },
    bankuser_id:{
      type:'string'
    },
    bankadmin_id:{
      type:'string'
    },
    jenis_pembayaran:{
      type:'string'
    },
    transfer_photo_url:{
      type:'string'
    },
    information:{
      type:'string'
    },
    status:{
      type:'string'
    }
  },
  connection:'appngasaldb'
};

