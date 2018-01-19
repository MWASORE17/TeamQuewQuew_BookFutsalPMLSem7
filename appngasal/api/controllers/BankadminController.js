/**
 * BankadminController
 *
 * @description :: Server-side logic for managing bankadmins
 * @help        :: See http://sailsjs.org/#!/documentation/concepts/Controllers
 */

module.exports = {
	create: function(req, res, next){
        Bankadmin.create(req.params.all(),function bankadminCreated(err, bankadmin){
            if(err){
                console.log(err)
            
        }
        res.json(201,bankadmin);
        //res.send('oke')
        
        });
    },
    edit: function(req, res, next){
        Bankadmin.findOne(req.param('id'), function foundBankAdmin(err,bankadmin){
            if(err) return next(err);
            if(!bankadmin) return next('Bank Admin doesn\'t exist.');
            // res.view({
            //     user: user
            // });
        });
    },

    update: function(req, res, next){
        Bankadmin.update(req.param('id'),req.params.all(), function bankadminUpdated(err){
            if(err){
                return res.redirect('/bankadmin/' + req.param('id'));
            }
            res.json(201,bankadmin);
        });
    },
    delete: function(req, res, next){
        Bankadmin.findOne(req.param('id'), function foundBankAdmin(err,bankadmin){
            if(err) return next(err);

            if(!bankadmin) return next('Bank Admin doesn\'t exist.');

            Bankadmin.destroy(req.param('id'), function bankadminDestroyed(err){
                if(err) return next(err);
            });
            res.json(202,bankadmin);
        });
    }
};

