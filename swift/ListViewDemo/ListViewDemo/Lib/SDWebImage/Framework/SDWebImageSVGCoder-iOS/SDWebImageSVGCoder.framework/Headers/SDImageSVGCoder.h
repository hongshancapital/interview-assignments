//
//  SDImageSVGCoder.h
//  SDWebImageSVGCoder
//
//  Created by DreamPiggy on 2018/9/27.
//

#if __has_include(<SDWebImage/SDWebImage.h>)
#import <SDWebImage/SDWebImage.h>
#else
@import SDWebImage;
#endif

NS_ASSUME_NONNULL_BEGIN

/***
SDImageSVGCoder is a SVG image coder, which use the built-in UIKit/AppKit method to decode SVG images. The SVG was implemented in iOS 13/macOS 10.15 with Symbol Image format, which is subset of SVG 1.1 or SVG 2 spec. If you find your SVG ccould not rendered correctly, please convert it by checking https://developer.apple.com/documentation/xcode/creating_custom_symbol_images_for_your_app

@note If you call the coder directly, use the coder option (See `SDWebImageSVGCoderDefine.h`) instead of the context option.
*/
API_AVAILABLE(macos(10.15), ios(13.0), watchos(6.0), tvos(13.0))
@interface SDImageSVGCoder : NSObject <SDImageCoder>

@property (nonatomic, class, readonly) SDImageSVGCoder *sharedCoder;

@end

NS_ASSUME_NONNULL_END
