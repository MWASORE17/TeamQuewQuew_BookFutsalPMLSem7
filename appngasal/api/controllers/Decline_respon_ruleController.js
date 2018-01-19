/**
 * Decline_respon_ruleController
 *
 * @description :: Server-side logic for managing decline_respon_rules
 * @help        :: See http://sailsjs.org/#!/documentation/concepts/Controllers
 */

module.exports = {
	create: function(req, res, next){
        Decline_respon_rule.create(req.params.all(),function declineresponruleCreated(err, declineresponrule){
            if(err){
                console.log(err)
            
        }
        res.json(201,declineresponrule);
        //res.send('oke')
        
        });
    },
    edit: function(req, res, next){
        Decline_respon_rule.findOne(req.param('id'), function founddeclineresponrule(err,declineresponrule){
            if(err) return next(err);
            if(!declineresponrule) return next('declineresponrule doesn\'t exist.');
            // res.view({
            //     user: user
            // });
        });
    },

    update: function(req, res, next){
        Decline_respon_rule.update(req.param('id'),req.params.all(), function declineresponruleUpdated(err){
            if(err){
                return res.redirect('/Decline_respon_rule/' + req.param('id'));
            }
            res.json(201,declineresponrule);
        });
    },
    delete: function(req, res, next){
        Decline_respon_rule.findOne(req.param('id'), function founddeclineresponrule(err,declineresponrule){
            if(err) return next(err);

            if(!declineresponrule) return next('declineresponrule doesn\'t exist.');

            Decline_respon_rule.destroy(req.param('id'), function declineresponruleDestroyed(err){
                if(err) return next(err);
            });
            res.json(202,declineresponrule);
        });
    }
};

