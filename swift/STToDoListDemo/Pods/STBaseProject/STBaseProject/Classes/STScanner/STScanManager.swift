//
//  STScanManager.swift
//  STBaseProject
//
//  Created by stack on 2018/3/14.
//  Copyright © 2018 ST. All rights reserved.
//

import UIKit
import Photos
import AVFoundation
import AssetsLibrary

public typealias STScanFinishBlock = (_ result: String) -> Void

open class STScanManager: STImagePickerManager {
    
    var delayQRAction: Bool = false
    var delayBarAction: Bool = false
    var scanFinishBlock: STScanFinishBlock?

    var scanType: STScanType?
    open var scanRect: CGRect?
    open var scanRectView: STScanView?
    
    private var device: AVCaptureDevice?
    private var session: AVCaptureSession?
    private var input: AVCaptureDeviceInput?
    private var output: AVCaptureMetadataOutput?
    private var preview: AVCaptureVideoPreviewLayer?
    
    public override init(presentViewController: UIViewController) {
        super.init(presentViewController: presentViewController)
    }
    
    /// 初始化二维码扫描控制器
    ///
    ///  - Parameter type: 扫码类型
    ///  - Parameter presentViewController: 当前类所在viewController
    ///  - Parameter onFinish: 扫描完成回调
    ///
    public init(qrType type: STScanType, presentViewController: UIViewController, onFinish: @escaping(STScanFinishBlock)) {
        super.init(presentViewController: presentViewController)
        self.scanType = type
        self.scanFinishBlock = onFinish
        self.configScanManager()
    }

    /// 扫描完成回调
    ///
    ///  - Parameter block: 扫描完成回调
    ///
    public func st_scanFinishCallback(block: @escaping STScanFinishBlock) -> Void {
        self.scanFinishBlock = block
    }
    
    /// 开始扫描
    public func st_beginScan() {
        if let newSession = self.session {
            newSession.startRunning()
        }
    }
    
    /// 停止扫描
    public func st_stopScan() {
        if let newSession = self.session, newSession.isRunning {
            newSession.stopRunning()
        }
    }
    
    /// 识别二维码
    /// - Parameter image: UIImage对象
    /// - Parameter onFinish: 识别成功回调，返回识别字符串
    /// - Parameter onFailed: 识别失败回调，返回Error
    ///
    public class func st_recognizeQrCodeImage(image: UIImage, onFinish: @escaping(String) -> Void, onFailed: @escaping(Error) -> Void) {
        if STDeviceInfo.currentSysVersion().doubleValue < 8.0 {
            DispatchQueue.main.async {
                onFailed(NSError.init(domain: "Only supports iOS 8.0 system or higher", code: 0, userInfo: [:]))
            }
            return
        }
        
        if UIImage.st_imageIsEmpty(image: image) {
            DispatchQueue.main.async {
                onFailed(NSError.init(domain: "image is empty", code: 0, userInfo: [:]))
            }
            return
        }
        
        let context: CIContext = CIContext()
        let detector: CIDetector = CIDetector.init(ofType: CIDetectorTypeQRCode, context: context, options: [CIDetectorAccuracy : CIDetectorAccuracyHigh]) ?? CIDetector()
        
        let features: [CIFeature] = detector.features(in: CIImage.init(image: image) ?? CIImage.empty())
        if features.count >= 1 {
            let feature: CIQRCodeFeature = features[0] as! CIQRCodeFeature
            let scanResult = feature.messageString
            DispatchQueue.main.async {
                onFinish(scanResult ?? "")
            }
        } else {
            DispatchQueue.main.async {
                onFailed(NSError.init(domain: "scan error", code: 0, userInfo: [:]))
            }
        }
    }
    
    /// 生成二维码【白底黑色】
    /// - Parameter content: 二维码内容字符串【数字、字符、链接等】
    /// - Parameter qrSize: 生成图片的大小
    /// - Parameter onFinish: 识别成功回调，返回UIImage图片对象
    /// - Parameter onFailed: 识别失败回调，返回Error
    ///
    public class func st_createQRImageWithString(content: String, qrSize: CGSize, onFinish: @escaping(UIImage) -> Void, onFailed: @escaping(Error) -> Void) {
        self.st_createQRImageWithString(content: content, qrSize: qrSize, qrColor: UIColor.black, bkColor: UIColor.white, onFinish: onFinish, onFailed: onFailed)
    }
    
    /// 生成二维码【自定义颜色】
    /// - Parameter content: 二维码内容字符串【数字、字符、链接等】
    /// - Parameter qrSize: 生成图片的大小
    /// - Parameter qrColor: 二维码颜色
    /// - Parameter bkColor: 背景色
    /// - Parameter onFinish: 识别成功回调，返回UIImage图片对象
    /// - Parameter onFailed: 识别失败回调，返回Error
    ///
    public class func st_createQRImageWithString(content: String, qrSize: CGSize, qrColor: UIColor, bkColor: UIColor, onFinish: @escaping(UIImage) -> Void, onFailed: @escaping(Error) -> Void) {
        if content.count < 1 {
            DispatchQueue.main.async {
                onFailed(NSError.init(domain: "content is nil", code: 0, userInfo: [:]))
            }
            return
        }
        
        if qrSize == CGSize.zero {
            DispatchQueue.main.async {
                onFailed(NSError.init(domain: "qrSize is zero", code: 0, userInfo: [:]))
            }
            return
        }
        
        let stringData = content.data(using: String.Encoding.utf8)
        let qrFilter: CIFilter = CIFilter.init(name: "CIQRCodeGenerator") ?? CIFilter()
        qrFilter.setValue(stringData, forKey: "inputMessage")
        qrFilter.setValue("H", forKey: "inputCorrectionLevel")
        let colorFilter: CIFilter = CIFilter.init(
            name: "CIFalseColor",
            parameters: ["inputImage": qrFilter.outputImage ?? CIImage.empty(),
                        "inputColor0": CIColor.init(cgColor: qrColor.cgColor),
                        "inputColor1": CIColor.init(cgColor: bkColor.cgColor)]) ?? CIFilter()
        let qrImage: CIImage = colorFilter.outputImage ?? CIImage.empty()
        let cgImage: CGImage = CIContext.init().createCGImage(qrImage, from: qrImage.extent)!
        
        UIGraphicsBeginImageContext(qrSize)
        let cgContext: CGContext = UIGraphicsGetCurrentContext()!
        cgContext.interpolationQuality = .none
        cgContext.scaleBy(x: 1.0, y: -1.0)
        cgContext.draw(cgImage, in: cgContext.boundingBoxOfClipPath)
        let codeImage: UIImage = UIGraphicsGetImageFromCurrentImageContext() ?? UIImage()
        UIGraphicsEndImageContext()
        DispatchQueue.main.async {
            onFinish(codeImage)
        }
    }

    /// 生成条形码【白底黑色】
    /// - Parameter content: 条码内容【一般是数字】
    /// - Parameter barSize: 生成条码图片的大小
    /// - Parameter onFinish: 识别成功回调，返回UIImage图片对象
    /// - Parameter onFailed: 识别失败回调，返回Error
    ///
    public class func st_createBarCodeImageWithString(content: String, barSize: CGSize, onFinish: @escaping(UIImage) -> Void, onFailed: @escaping(Error) -> Void) {
        self.st_createBarCodeImageWithString(content: content, barSize: barSize, barColor: UIColor.black, barBgColor: UIColor.white, onFinish: onFinish, onFailed: onFailed)
    }
    
    /// 生成条形码【自定义颜色】
    /// - Parameter content: 条码内容【一般是数字】
    /// - Parameter barSize: 生成条码图片的大小
    /// - Parameter barColor: 条形码颜色
    /// - Parameter barBgColor: 条形码背景色
    /// - Parameter onFinish: 识别成功回调，返回UIImage图片对象
    /// - Parameter onFailed: 识别失败回调，返回Error
    ///
    public class func st_createBarCodeImageWithString(content: String, barSize: CGSize, barColor: UIColor, barBgColor: UIColor, onFinish: @escaping(UIImage) -> Void, onFailed: @escaping(Error) -> Void) {
        if content.count < 1 {
            DispatchQueue.main.async {
                onFailed(NSError.init(domain: "content is nil", code: 0, userInfo: [:]))
            }
            return
        }
        
        if barSize == CGSize.zero {
            DispatchQueue.main.async {
                onFailed(NSError.init(domain: "barSize is zero", code: 0, userInfo: [:]))
            }
            return
        }
        
        let stringData = content.data(using: String.Encoding.utf8)
        let qrFilter: CIFilter = CIFilter.init(name: "CICode128BarcodeGenerator") ?? CIFilter()
        qrFilter.setValue(stringData, forKey: "inputMessage")
        let colorFilter: CIFilter = CIFilter.init(
            name: "CIFalseColor",
            parameters: ["inputImage": qrFilter.outputImage ?? CIImage.empty(),
                         "inputColor0": CIColor.init(cgColor: barColor.cgColor),
                         "inputColor1": CIColor.init(cgColor: barBgColor.cgColor)]) ?? CIFilter()
        let qrImage: CIImage = colorFilter.outputImage ?? CIImage.empty()
        let cgImage: CGImage = CIContext.init().createCGImage(qrImage, from: qrImage.extent)!
        UIGraphicsBeginImageContext(barSize)
        let cgContext: CGContext = UIGraphicsGetCurrentContext()!
        cgContext.interpolationQuality = .none
        cgContext.scaleBy(x: 1.0, y: -1.0)
        cgContext.draw(cgImage, in: cgContext.boundingBoxOfClipPath)
        let codeImage: UIImage = UIGraphicsGetImageFromCurrentImageContext() ?? UIImage()
        UIGraphicsEndImageContext()
        DispatchQueue.main.async {
            onFinish(codeImage)
        }
    }
    
    /// 调整二维码清晰度，添加水印图片
    /// - Parameter content: 模糊的二维码图片字符串
    /// - Parameter size: 二维码的宽高
    /// - Parameter waterImg: 水印图片
    /// - Parameter barBgColor: 条形码背景色
    /// - Parameter onFinish: 识别成功回调，返回添加水印图片后，清晰的二维码图片
    ///
    public class func st_getHDImgWithCIImage(content: String, size: CGSize, waterImage: UIImage, waterImageSize: CGSize, onFinish: @escaping(Result<UIImage, Error>) -> Void) {
        if content.count < 1 {
            DispatchQueue.main.async {
                onFinish(.failure(NSError.init(domain: "content is nil!", code: 0, userInfo: [:])))
            }
            return
        }
        
        if size == CGSize.zero {
            DispatchQueue.main.async {
                onFinish(.failure(NSError.init(domain: "size is zero!", code: 0, userInfo: [:])))
            }
            return
        }
        
        if UIImage.st_imageIsEmpty(image: waterImage) == true {
            DispatchQueue.main.async {
                onFinish(.failure(NSError.init(domain: "waterImageSize is nil!", code: 0, userInfo: [:])))
            }
            return
        }
        
        if waterImageSize == CGSize.zero {
            DispatchQueue.main.async {
                onFinish(.failure(NSError.init(domain: "waterImageSize is zero!", code: 0, userInfo: [:])))
            }
            return
        }
        
        let stringData = content.data(using: String.Encoding.utf8)
        let qrFilter: CIFilter = CIFilter.init(name: "CIQRCodeGenerator") ?? CIFilter()
        qrFilter.setValue(stringData, forKey: "inputMessage")
        qrFilter.setValue("H", forKey: "inputCorrectionLevel")
        
        let img: CIImage = qrFilter.outputImage ?? CIImage.empty()
        let extent: CGRect = img.extent.integral
        let scale: CGFloat = min(size.width / extent.width, size.height / extent.height)
        let width: CGFloat = extent.width * scale
        let height: CGFloat = extent.height * scale;
        let cs: CGColorSpace = CGColorSpaceCreateDeviceGray()
        let bitmapRef: CGContext = CGContext(data: nil, width: Int(width), height: Int(height), bitsPerComponent: 8, bytesPerRow: 0, space: cs, bitmapInfo: CGImageAlphaInfo.none.rawValue)!
        let ciContext: CIContext = CIContext.init()
        let bitmapImage: CGImage = ciContext.createCGImage(img, from: extent)!
        bitmapRef.interpolationQuality = CGInterpolationQuality.none
        bitmapRef.scaleBy(x: scale, y: scale)
        bitmapRef.draw(bitmapImage, in: extent)
        let scaledImage: CGImage = bitmapRef.makeImage()!
        let outputImage: UIImage = UIImage.init(cgImage: scaledImage)
        UIGraphicsBeginImageContextWithOptions(outputImage.size, false, UIScreen.main.scale)
        outputImage.draw(in: CGRect.init(x: 0, y: 0, width: size.width, height: size.height))
        //把水印图片画到生成的二维码图片上，注意尺寸不要太大（根据上面生成二维码设置的纠错程度设置），否则有可能造成扫不出来
        let waterImgH: CGFloat = waterImageSize.height
        let waterImgW: CGFloat = waterImageSize.width
        waterImage.draw(in: CGRect.init(x: (size.width - waterImgW) / 2.0, y: (size.height - waterImgH) / 2.0, width: waterImgW, height: waterImgH))
        let newPic: UIImage = UIGraphicsGetImageFromCurrentImageContext() ?? UIImage()
        UIGraphicsEndImageContext()
        DispatchQueue.main.async {
            onFinish(.success(newPic))
        }
    }
    
    public override func imagePickerController(_ picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [UIImagePickerController.InfoKey : Any]) {
        picker.dismiss(animated: true) {}
        var image: UIImage? = info[UIImagePickerController.InfoKey.editedImage] as? UIImage
        if image == nil {
            image = info[UIImagePickerController.InfoKey.originalImage] as? UIImage
        }
        self.detailSelectPhoto(image: image ?? UIImage())
    }
    
    public func detailSelectPhoto(image: UIImage) -> Void {
        STScanManager.st_recognizeQrCodeImage(image: image, onFinish: {[weak self] (result) in
            guard let strongSelf = self else { return }
            strongSelf.st_renderUrlStr(url: result)
        }) {[weak self] (error) in
            guard let strongSelf = self else { return }
            strongSelf.st_renderUrlStr(url: "")
        }
    }
    
    private func st_renderUrlStr(url: String) -> Void {
        if let newScanFinish = self.scanFinishBlock {
            DispatchQueue.main.async {
                newScanFinish(url)
            }
        }
    }
    
    private func configScanManager() {
        self.st_scanDevice()
        self.st_drawScanView()
    }
    
    private func st_scanDevice() -> Void {
        self.st_isAvailableCamera {[weak self] (openSourceError) in
            guard let strongSelf = self else { return }
            if openSourceError == .openSourceOK {
                strongSelf.device = AVCaptureDevice.default(for: .video)
                strongSelf.input = try? AVCaptureDeviceInput.init(device: strongSelf.device!)
                strongSelf.output = AVCaptureMetadataOutput.init()
                strongSelf.output?.setMetadataObjectsDelegate(self, queue: DispatchQueue.main)
                strongSelf.session = AVCaptureSession()
                if let newSession = strongSelf.session {
                    newSession.canSetSessionPreset(AVCaptureSession.Preset.inputPriority)
                    if let newInput = strongSelf.input, newSession.canAddInput(newInput) == true {
                        newSession.addInput(newInput)
                    }
                    if let newOutput = strongSelf.output, newSession.canAddOutput(newOutput) == true {
                        newSession.addOutput(newOutput)
                        newOutput.metadataObjectTypes = [AVMetadataObject.ObjectType.qr]
                        newOutput.rectOfInterest = strongSelf.scanRect ?? CGRect.zero
                    }
                    strongSelf.preview = AVCaptureVideoPreviewLayer.init(session: newSession)
                    if let newPreview = strongSelf.preview {
                        newPreview.videoGravity = .resizeAspectFill
                        newPreview.frame = UIScreen.main.bounds
                        strongSelf.presentVC?.view.layer.insertSublayer(newPreview, at: 0)
                    }
                }
                strongSelf.output?.metadataObjectTypes = [AVMetadataObject.ObjectType.qr]
                strongSelf.output?.rectOfInterest = NSCoder.cgRect(for: strongSelf.st_scanRectWithScale(scale: 1)[0] as! String)
            }
        }
    }

    private func st_drawScanView() -> Void {
        self.scanRectView = STScanView.init(frame: CGRect.init(x: 0, y: 0, width: self.presentVC?.view.bounds.size.width ?? UIScreen.main.bounds.size.width, height: self.presentVC?.view.bounds.size.height ?? UIScreen.main.bounds.size.height))
        self.scanRectView?.st_configScanType(scanType: self.scanType ?? STScanType.STScanTypeQrCode)
        self.presentVC?.view.addSubview(self.scanRectView!)
    }
    
    private func st_scanRectWithScale(scale: CGFloat) -> NSArray {
        let windowSize = UIScreen.main.bounds.size
        let left = 60.0 / scale
        let scanSize = CGSize.init(width: self.presentVC?.view.frame.size.width ?? UIScreen.main.bounds.size.width - left * 2.0,
                                   height: (self.presentVC?.view.frame.size.width ?? UIScreen.main.bounds.size.width - left * 2.0) / scale)
        var scanRect = CGRect.init(x: (windowSize.width - scanSize.width) / 2.0,
                                   y: (windowSize.height - scanSize.height) / 2.0,
                                   width: scanSize.width,
                                   height: scanSize.height)
        scanRect = CGRect.init(x: scanRect.origin.y / windowSize.height,
                               y: scanRect.origin.x / windowSize.width,
                               width: scanRect.size.height / windowSize.height,
                               height: scanRect.size.width / windowSize.width)
        return [NSCoder.string(for: scanRect), NSCoder.string(for: scanSize)]
    }
}

extension STScanManager: AVCaptureMetadataOutputObjectsDelegate {
    public func metadataOutput(_ output: AVCaptureMetadataOutput, didOutput metadataObjects: [AVMetadataObject], from connection: AVCaptureConnection) {
        if metadataObjects.count < 1 { return }
        self.st_stopScan()
        let metadataObject: AVMetadataMachineReadableCodeObject = metadataObjects.first as! AVMetadataMachineReadableCodeObject
        self.st_renderUrlStr(url: metadataObject.stringValue ?? "")
    }
}
