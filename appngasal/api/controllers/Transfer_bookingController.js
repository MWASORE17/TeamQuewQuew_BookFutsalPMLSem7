/**
 * Transfer_bookingController
 *
 * @description :: Server-side logic for managing transfer_bookings
 * @help        :: See http://sailsjs.org/#!/documentation/concepts/Controllers
 */

module.exports = {
	create: function(req, res, next){
        Transfer_booking.create(req.params.all(),function transferbookingCreated(err, transferbooking){
            if(err){
                console.log(err)
            
        }
        res.json(201,transferbooking);
        //res.send('oke')
        
        });
    },
    edit: function(req, res, next){
        Transfer_booking.findOne(req.param('id'), function foundtransferbooking(err,transferbooking){
            if(err) return next(err);
            if(!transferbooking) return next('transferbooking doesn\'t exist.');
            // res.view({
            //     user: user
            // });
        });
    },

    update: function(req, res, next){
        Transfer_booking.update(req.param('id'),req.params.all(), function transferbookingUpdated(err){
            if(err){
                return res.redirect('/transfer_booking/' + req.param('id'));
            }
            res.json(201,transferbooking);
        });
    },
    delete: function(req, res, next){
        Transfer_booking.findOne(req.param('id'), function foundtransferbooking(err,transferbooking){
            if(err) return next(err);

            if(!transferbooking) return next('transferbooking doesn\'t exist.');

            Transfer_booking.destroy(req.param('id'), function transferbookingDestroyed(err){
                if(err) return next(err);
            });
            res.json(202,transferbooking);
        });
    }
};

