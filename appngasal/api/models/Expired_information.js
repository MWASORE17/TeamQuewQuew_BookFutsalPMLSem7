/**
 * Expired_information.js
 *
 * @description :: TODO: You might write a short summary of how this model works and what it represents here.
 * @docs        :: http://sailsjs.org/documentation/concepts/models-and-orm/models
 */

module.exports = {

  attributes: {
    booking_id:{
      type:'string'
    },
    exp_date:{
        type:'string'
    },
    exp_time:{
        type:'string'
    },
    status_sent:{
        type:'string'
    },
    status_taken_action:{
        type:'string'
    }
  },
  connection:'appngasaldb'
};

