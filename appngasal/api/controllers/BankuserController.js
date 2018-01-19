/**
 * BankuserController
 *
 * @description :: Server-side logic for managing bankusers
 * @help        :: See http://sailsjs.org/#!/documentation/concepts/Controllers
 */

module.exports = {
	create: function(req, res, next){
        Bankuser.create(req.params.all(),function bankuserCreated(err, bankuser){
            if(err){
                console.log(err)
            
        }
        res.json(201,bankuser);
        //res.send('oke')
        
        });
    },
    edit: function(req, res, next){
        Bankuser.findOne(req.param('id'), function foundBankUsers(err,bankuser){
            if(err) return next(err);
            if(!bankuser) return next('Bank User doesn\'t exist.');
            // res.view({
            //     user: user
            // });
        });
    },

    update: function(req, res, next){
        Bankuser.update(req.param('id'),req.params.all(), function bankuserUpdated(err){
            if(err){
                return res.redirect('/bankuser/' + req.param('id'));
            }
            res.json(201,bankuser);
        });
    },
    delete: function(req, res, next){
        Bankuser.findOne(req.param('id'), function foundBankUsers(err,bankuser){
            if(err) return next(err);

            if(!bankuser) return next('Bank User doesn\'t exist.');

            Bankuser.destroy(req.param('id'), function bankuserDestroyed(err){
                if(err) return next(err);
            });
            res.json(202,bankuser);
        });
    }
};

