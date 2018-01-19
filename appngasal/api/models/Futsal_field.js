/**
 * Futsal_field.js
 *
 * @description :: TODO: You might write a short summary of how this model works and what it represents here.
 * @docs        :: http://sailsjs.org/documentation/concepts/models-and-orm/models
 */

module.exports = {
  
  attributes: {
      futsal_name :{
        type:'string'
      },
      location:{
        type:'string'
      },
      address:{
        type:'string'
      },
      type_field:{
        type:'string'
      },
      price:{
        type:'integer'
      },
      description:{
        type:'string'
      },
      districts:{
        type:'string'
      },
      photo_url:{
        type:'string'
      },

  },

  connection:'appngasaldb'
};

