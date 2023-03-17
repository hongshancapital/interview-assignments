//
//  SDWebImageSVGCoderDefine.h
//  SDWebImageSVGCoder
//
//  Created by DreamPiggy on 2018/10/11.
//

#if __has_include(<SDWebImage/SDWebImage.h>)
#import <SDWebImage/SDWebImage.h>
#else
@import SDWebImage;
#endif

/**
 A BOOL value which specify whether we prefer the actual bitmap representation instead of vector representation for SVG image. This is because the UIImage on iOS 13+ (NSImage on macOS 10.15+) can use the vector image format, which support dynamic scale without losing any detail. However, for some image processing logic, user may need the actual bitmap representation to manage pixels. (NSNumber)
 If you don't provide this value, use NO for default value and prefer the vector format when possible.
 @note Deprecated, use `SDWebImageContextImageThumbnailPixelSize`. Pass CGSize.zero means the viewBox size of SVG.
 */
FOUNDATION_EXPORT SDWebImageContextOption _Nonnull const SDWebImageContextSVGPrefersBitmap API_DEPRECATED_WITH_REPLACEMENT("SDWebImageContextImageThumbnailPixelSize", macos(10.15, 10.15), ios(13.0, 13.0), watchos(6.0, 6.0), tvos(13.0, 13.0));

/**
 A CGSize raw value which specify the desired SVG image size during image loading. Because vector image like SVG format, may not contains a fixed size, or you want to get a larger size bitmap representation UIImage. (NSValue)
 If you don't provide this value, use viewBox size of SVG for default value.
 */
FOUNDATION_EXPORT SDWebImageContextOption _Nonnull const SDWebImageContextSVGImageSize API_DEPRECATED_WITH_REPLACEMENT("SDWebImageContextImageThumbnailPixelSize", macos(10.15, 10.15), ios(13.0, 13.0), watchos(6.0, 6.0), tvos(13.0, 13.0));

/**
 A BOOL value which specify the whether SVG image should keep aspect ratio during image loading. Because when you specify image size via `SDWebImageContextSVGImageSize`, we need to know whether to keep aspect ratio or not when image size aspect ratio is not equal to SVG viewBox size aspect ratio. (NSNumber)
 If you don't provide this value, use YES for default value.
 */
FOUNDATION_EXPORT SDWebImageContextOption _Nonnull const SDWebImageContextSVGImagePreserveAspectRatio API_DEPRECATED_WITH_REPLACEMENT("SDWebImageContextImagePreserveAspectRatio", macos(10.15, 10.15), ios(13.0, 13.0), watchos(6.0, 6.0), tvos(13.0, 13.0));
