/**
 * Decline_respon_rule.js
 *
 * @description :: TODO: You might write a short summary of how this model works and what it represents here.
 * @docs        :: http://sailsjs.org/documentation/concepts/models-and-orm/models
 */

module.exports = {

  attributes: {
    booking_id:{
      type:'string'
    },
    decline_date:{
      type:'string'
    },
    decline_time:{
      type:'string'
    }
  },
  connection:'appngasaldb'
};

