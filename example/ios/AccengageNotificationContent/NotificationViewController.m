//
//  NotificationViewController.m
//  AccengageNotificationContent
//
//  Copyright © 2017 Accengage. All rights reserved.
//

#import "NotificationViewController.h"
#import <UserNotifications/UserNotifications.h>
#import <UserNotificationsUI/UserNotificationsUI.h>
#import <AccengageExtension/AccengageExtension.h>

@interface NotificationViewController () <UNNotificationContentExtension>

@property IBOutlet UILabel *body;
@property IBOutlet UILabel *header;
@property IBOutlet UILabel *date;

@property IBOutlet UIImageView *poster;
@property IBOutlet UIImageView *director;
@property IBOutlet UIActivityIndicatorView *loader;

@property NSArray<UNNotificationAttachment *> *attachements;

@end

@implementation NotificationViewController


- (void)dealloc {
  [self.attachements enumerateObjectsUsingBlock:^(UNNotificationAttachment *att, NSUInteger idx, BOOL *stop) {
    [att.URL stopAccessingSecurityScopedResource];
  }];
}


- (void)didReceiveNotification:(UNNotification *)notification {
  self.attachements = notification.request.content.attachments;
  
  // Set body
  UNNotificationContent *content = notification.request.content;
  self.body.text = content.body;
  
  // Set header info
  NSString *string = [NSString stringWithFormat:@"%@\nDe %@",content.title, content.subtitle];
  NSMutableAttributedString * attributedString = [[NSMutableAttributedString alloc] initWithString:string];
  
  [attributedString addAttribute:NSFontAttributeName value:[UIFont fontWithName:@"HelveticaNeue-CondensedBlack" size:21.] range:[string rangeOfString:content.title]];
  [attributedString addAttribute:NSFontAttributeName value:[UIFont fontWithName:@"HelveticaNeue-Bold" size:13.] range:[string rangeOfString:content.subtitle]];
  self.header.attributedText = attributedString;
  [self.header sizeToFit];
  
  string = [NSString stringWithFormat:@"Sortie le %@",content.userInfo[@"date"]];
  attributedString = [[NSMutableAttributedString alloc] initWithString:string];
  [attributedString addAttribute:NSFontAttributeName value:[UIFont fontWithName:@"HelveticaNeue-BoldItalic" size:13.] range:[string rangeOfString:content.userInfo[@"date"]]];
  self.date.attributedText = attributedString;
  
  // Set images
  [self.attachements enumerateObjectsUsingBlock:^(UNNotificationAttachment *att, NSUInteger idx, BOOL *stop) {
    
    if ([att.identifier isEqualToString:@"poster"] && [att.URL startAccessingSecurityScopedResource]) {
      
      self.poster.image = [UIImage imageWithContentsOfFile:att.URL.path];
      
      UIBlurEffect *blur = [UIBlurEffect effectWithStyle:UIBlurEffectStyleDark];
      UIVisualEffectView *effectView = [[UIVisualEffectView alloc] initWithEffect:blur];
      effectView.autoresizingMask = UIViewAutoresizingFlexibleHeight | UIViewAutoresizingFlexibleWidth;
      effectView.frame = self.view.frame;
      effectView.alpha = 0.8f;
      [self.poster addSubview:effectView];
    }
    
    if ([att.identifier isEqualToString:@"director"] && [att.URL startAccessingSecurityScopedResource]) {
      self.director.image = [UIImage imageWithContentsOfFile:att.URL.path];;
    }
  }];
}

- (void)didReceiveNotificationResponse:(UNNotificationResponse *)response
                     completionHandler:(void (^)(UNNotificationContentExtensionResponseOption))completion {
  
  if (![ACCNotificationContentExtension shouldDelayDismissForNotificationResponse:response]) {
    completion(UNNotificationContentExtensionResponseOptionDismissAndForwardAction);
  }
  else {
    [self startLoading];
    
    dispatch_after(dispatch_time(DISPATCH_TIME_NOW, 3 * NSEC_PER_SEC), dispatch_get_main_queue(), ^{
      completion(UNNotificationContentExtensionResponseOptionDismissAndForwardAction);
    });
  }
}

- (void)startLoading {
  UIBlurEffect *blur = [UIBlurEffect effectWithStyle:UIBlurEffectStyleDark];
  UIVisualEffectView *effectView = [[UIVisualEffectView alloc] initWithEffect:blur];
  effectView.autoresizingMask = UIViewAutoresizingFlexibleHeight | UIViewAutoresizingFlexibleWidth;
  effectView.frame = self.view.frame;
  effectView.alpha = 0.8f;
  [self.view insertSubview:effectView belowSubview:self.loader];
  
  [self.loader startAnimating];
}

@end
