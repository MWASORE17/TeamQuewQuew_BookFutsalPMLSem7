/**
 * WalletController
 *
 * @description :: Server-side logic for managing wallets
 * @help        :: See http://sailsjs.org/#!/documentation/concepts/Controllers
 */

module.exports = {
	create: function(req, res, next){
        Wallet.create(req.params.all(),function walletCreated(err, wallet){
            if(err){
                console.log(err)
            
        }
        res.json(201,wallet);
        //res.send('oke')
        
        });
    },
    edit: function(req, res, next){
        Wallet.findOne(req.param('id'), function foundWallet(err,wallet){
            if(err) return next(err);
            if(!wallet) return next('Wallet doesn\'t exist.');
            // res.view({
            //     user: user
            // });
        });
    },

    update: function(req, res, next){
        Wallet.update(req.param('id'),req.params.all(), function walletUpdated(err){
            if(err){
                return res.redirect('/wallet/' + req.param('id'));
            }
            res.json(201,wallet);
        });
    },
    delete: function(req, res, next){
        Wallet.findOne(req.param('id'), function foundWallet(err,wallet){
            if(err) return next(err);

            if(!wallet) return next('wallet doesn\'t exist.');

            Wallet.destroy(req.param('id'), function walletDestroyed(err){
                if(err) return next(err);
            });
            res.json(202,wallet);
        });
    }
       
    
      
};


