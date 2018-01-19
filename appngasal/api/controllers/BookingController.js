/**
 * BookingController
 *
 * @description :: Server-side logic for managing bookings
 * @help        :: See http://sailsjs.org/#!/documentation/concepts/Controllers
 */

module.exports = {
    find :function(req,res,next){
        var tgl = req.param('tanggal');
        var jam = req.param('jam')
        var jam1 = req.param('jam1')
        
        var futsal_field_id = req.param('futsal_field_id')
            Booking.findOne({
                        date:tgl,
                        time:jam,
                        
                        futsal_field_id:futsal_field_id
            },
                {   
                        date:tgl,
                        time1:jam1,
                        futsal_field_id:futsal_field_id
                    
            }).exec(function (err, bookings){
                if (err) {
                  return res.serverError(err);
                }
                if (bookings) {
                  return res.send('false');
                }
              
                
                return res.send('true');
              });
        
    },
    ganti: function(req, res, next){
        var total = req.param('total_transfer');
        var status = req.param('status');
        if(status=="Dibatalkan"){
            Booking.update(req.param('id'),req.params.all(), function transferTopupUpdated(err,transfer_topup){
                if(err){
                    return res.redirect('/booking/' + req.param('id'));
                }
                res.redirect('admin/booking/')
            });
        }
        else{
        User.findOne(req.param('user_id'), function foundUser(err,user){
            if(err) return next(err);
            if(!user) return next('User doesn\'t exist.');

            console.log(user.balance);
            console.log(total);
            
            var blance = parseInt(user.balance);
            blance = blance-parseInt(total);
            console.log(blance)
            User.update({id:req.param('user_id')}
            ,
            {
                balance:blance

          }).exec(function(err, user) {
            if (err) { return res.serverError(err) }
            // if it was successful return the registry in the response
            console.log(user)
})
    });
        Booking.update(req.param('id'),req.params.all(), function transferTopupUpdated(err,transfer_topup){
            if(err){
                return res.redirect('/booking/' + req.param('id'));
            }
            res.redirect('admin/booking/')
        });
    }
},
    findall: function (req, res) {
        console.log("Inside findall..............");

        return Booking.find().then(function (bookings) {
            console.log("bookingService.findAll -- transfer_topup = " + bookings);
            return res.view("admin/booking/list", {
                status: 'OK',
                title: 'List of Booking',
                bookings: bookings
            });
        }).catch(function (err) {
            console.error("Error on ContactService.findAll");
            console.error(err);
            return res.view('500', {message: "Sorry, an error occurd - " + err});
        });
    },
    hitung : function(req,res,next){
        var user_id = req.param('user_id');

        Booking.count({user_id:user_id,status:"Selesai"}).exec(function countCB(error, found) {
            
           var sukses=found
           
           User.update({id:req.param('user_id')}
           ,
           {sukses:sukses
         }).exec(function(err, file) {
           if (err) { return res.serverError(err) }
           // if it was successful return the registry in the response
           
        })
    })

        Booking.count({user_id:user_id,status:"Dibatalkan"}).exec(function countCB(error, found) {
            
            var batal=found
            
            User.update({id:req.param('user_id')}
            ,
            {batal:batal
          }).exec(function(err, file) {
            if (err) { return res.serverError(err) }
            // if it was successful return the registry in the response
            return res.json(file)
         }) 
    })
},
    
	create: function(req, res, next){
        Booking.create(req.params.all(),function bookingCreated(err, booking){
            if(err){
                console.log(err)
            
        }
        res.json(201,booking);
        
        });
    },
    sakuku: function(req, res, next){
        
        Booking.create(req.params.all(),function bookingCreated(err, booking){
            if(err)
                console.log(err)

               
    })
    var total = req.param('total_transfer');
    User.findOne(req.param('user_id'), function foundUser(err,user){
        if(err) return next(err);
        if(!user) return next('User doesn\'t exist.');

        console.log(user.balance);
        console.log(total);
        var totalba=0;
        var blance = parseInt(user.balance);
        totalba = blance-parseInt(total);
        console.log(blance)
        User.update({id:req.param('user_id')}
        ,
        {
            balance:parseInt(totalba)

      }).exec(function(err, user) {
        if (err) { return res.serverError(err) }

        res.json(201,user)
        // if it was successful return the registry in the response
        console.log(user)
      });
})
},
    edit: function(req, res, next){
        var user_id = req.param('user_id')
        Booking.find({user_id:user_id}).exec(function(err,booking){
            if (err) {
                return res.serverError(err);
              }
              if (!booking) {
                return res.notFound('Could not find user_id, sorry.');
              }
            
              
              return res.json(booking);
            });
    },

    update: function(req, res, next){
        Booking.update(req.param('id'),req.params.all(), function bookingUpdated(err,booking){
            if(err){
                return res.redirect('/booking/' + req.param('id'));
            }
            res.json(201,booking);
        });
    },
    delete: function(req, res, next){
        Booking.findOne(req.param('id'), function foundBookings(err,booking){
            if(err) return next(err);

            if(!booking) return next('booking doesn\'t exist.');

            Booking.destroy(req.param('id'), function bookingDestroyed(err){
                if(err) return next(err);
            });
            res.redirect('/admin/booking/')
        });
    }
};

