/**
 * Transfer_topup.js
 *
 * @description :: TODO: You might write a short summary of how this model works and what it represents here.
 * @docs        :: http://sailsjs.org/documentation/concepts/models-and-orm/models
 */

module.exports = {

  attributes: {
	user_id:{
		type:'string'
	},
    total_transfer:{
      type:'integer'
    },
	jumlah_transfer:{
      type:'integer'
    },
    bankuser_name:{
      type:'string'
    },
	bankuser_account_number:{
      type:'string'
    },
    name_bankuser:{
      type:'string'
    },
	bankadmin_name:{
      type:'string'
    },
	bankadmin_account_number:{
      type:'string'
    },
    name_bankadmin:{
      type:'string'
    },
    transfer_date:{
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

