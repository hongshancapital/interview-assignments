//
//  STImagePickerManager.swift
//  STBaseProject
//
//  Created by stack on 2018/4/28.
//  Copyright © 2018 ST. All rights reserved.
//

import UIKit
import Photos
import AVFoundation
import AssetsLibrary

public enum STOpenSourceType {
    case camera
    case simulator
    case photoLibrary
    case unknown
}

public enum STOpenSourceError: LocalizedError {
    
    case unknown
    case openSourceOK
    case openCameraError
    case unsupportSimulator
    case openPhotoLibraryError
    case authorizationCameraFailed
    case authorizationPhotoLibraryFailed
    case imagePickerControllerDidCancelError

    var errorDescription: String {
        switch self {
        case .openCameraError:
            return "open Camera Error"
        case .openPhotoLibraryError:
            return "open PhotoLibrary Error"
        case .imagePickerControllerDidCancelError:
            return "image pickerController did cancel"
        case .openSourceOK:
            return ""
        case .authorizationCameraFailed:
            return "相机读取权限未开启，请到手机系统【设置】->【隐私】->【相机】开启相机的访问权限"
        case .authorizationPhotoLibraryFailed:
            return "相册读取权限未开启，请到手机系统【设置】->【隐私】->【相册】开启相册的访问权限"
        case .unknown:
            return "未知错误"
        case .unsupportSimulator:
            return "不支持模拟器"
        }
    }
}

public class STImagePickerModel: NSObject {
    public var editedImage: UIImage?
    public var originalImage: UIImage?
    public var openSourceError: STOpenSourceError?
}

public typealias STImagePickerDidFinish = (_ imagePickerModel: STImagePickerModel) -> Void

open class STImagePickerManager: NSObject {

    open var customImageSize: CGSize?
    weak var presentVC: UIViewController?
    var imagePickerDidFinish: STImagePickerDidFinish?
    open var imagePickerController: UIImagePickerController?

    deinit {
        self.presentVC = nil
        self.imagePickerDidFinish = nil
        if self.imagePickerController != nil {
            self.imagePickerController?.delegate = nil
            self.imagePickerController = nil
        }
        STLog("STImagePickerManager dealloc")
    }
    
    public override init() {
        super.init()
    }
    
    public init(presentViewController: UIViewController) {
        super.init()
        self.presentVC = presentViewController
        self.st_imagePickerViewController()
    }

    private func st_imagePickerViewController() -> Void {
        if self.imagePickerController == nil {
            self.imagePickerController = UIImagePickerController()
            self.imagePickerController?.delegate = self
            self.imagePickerController?.allowsEditing = true
        }
    }

    private func st_openPhotoLibrary(complection: @escaping(STImagePickerModel) -> Void) {
        self.st_isAvailablePhoto {[weak self] (openSourceError) in
            guard let strongSelf = self else { return }
            if openSourceError == .openSourceOK {
                strongSelf.imagePickerController?.sourceType = .photoLibrary
                strongSelf.presentVC?.present(strongSelf.imagePickerController ?? UIImagePickerController(), animated: true) {}
            } else {
                let pickerModel = STImagePickerModel()
                pickerModel.openSourceError = .openPhotoLibraryError
                complection(pickerModel)
            }
        }
    }

    private func st_openCamera(complection: @escaping(STImagePickerModel) -> Void) {
        self.st_isAvailableCamera {[weak self] (openSourceError) in
            guard let strongSelf = self else { return }
            if openSourceError == .openSourceOK {
                strongSelf.imagePickerController?.sourceType = .camera
                strongSelf.presentVC?.present(strongSelf.imagePickerController ?? UIImagePickerController(), animated: true) {}
            } else {
                let pickerModel = STImagePickerModel()
                pickerModel.openSourceError = .openCameraError
                complection(pickerModel)
            }
        }
    }

    open func st_openSystemOperation(openSourceType: STOpenSourceType, complection: @escaping(STImagePickerModel) -> Void) {
        self.imagePickerDidFinish = complection
        switch openSourceType {
        case .photoLibrary:
            self.st_openPhotoLibrary(complection: complection)
            break
        case .camera:
            self.st_openCamera(complection: complection)
            break
        default:
            break
        }
    }
    
    public func st_isAvailablePhoto(complection: @escaping(STOpenSourceError) -> Void) {
        if UIImagePickerController.isSourceTypeAvailable(.photoLibrary) == true {
            let authorizationStatus: PHAuthorizationStatus = PHPhotoLibrary.authorizationStatus()
            if authorizationStatus == .authorized {
                DispatchQueue.main.async {
                    complection(.openSourceOK)
                }
            } else if authorizationStatus == .restricted || authorizationStatus == .denied {
                DispatchQueue.main.async {
                    complection(.authorizationPhotoLibraryFailed)
                }
            } else if authorizationStatus == .notDetermined {
                PHPhotoLibrary.requestAuthorization { (status) in
                    if status == .authorized {
                        DispatchQueue.main.async {
                            complection(.openSourceOK)
                        }
                    } else if status == .restricted || status == .denied {
                        DispatchQueue.main.async {
                            complection(.authorizationPhotoLibraryFailed)
                        }
                    }
                }
            }
        } else {
            DispatchQueue.main.async {
                complection(.openPhotoLibraryError)
            }
        }
    }
    
    public func st_isAvailableCamera(complection: @escaping(STOpenSourceError) -> Void) {
        if UIImagePickerController.isSourceTypeAvailable(.camera) == true {
            let mediaType = AVMediaType.video
            let authorizationStatus: AVAuthorizationStatus = AVCaptureDevice.authorizationStatus(for: mediaType)
            if authorizationStatus == .authorized {
                DispatchQueue.main.async {
                    complection(.openSourceOK)
                }
            } else if authorizationStatus == .restricted || authorizationStatus == .denied {
                DispatchQueue.main.async {
                    complection(.authorizationCameraFailed)
                }
            } else if authorizationStatus == .notDetermined {
                AVCaptureDevice.requestAccess(for: mediaType) { (granted) in
                    if granted {
                        DispatchQueue.main.async {
                            complection(.openSourceOK)
                        }
                    } else {
                        DispatchQueue.main.async {
                            complection(.authorizationCameraFailed)
                        }
                    }
                }
            }
        } else {
            DispatchQueue.main.async {
                complection(.unsupportSimulator)
            }
        }
    }
}

extension STImagePickerManager: UIImagePickerControllerDelegate, UINavigationControllerDelegate {
    public func imagePickerController(_ picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [UIImagePickerController.InfoKey : Any]) {
        let originalImage: UIImage = info[UIImagePickerController.InfoKey.originalImage] as! UIImage
        let editedImage: UIImage = info[UIImagePickerController.InfoKey.editedImage] as! UIImage
        let newSize = self.customImageSize ?? CGSize.init(width: 200, height: 200)
        UIGraphicsBeginImageContext(newSize)
        editedImage.draw(in: CGRect.init(x: 0, y: 0, width: newSize.width, height: newSize.height))
        let newImage = UIGraphicsGetImageFromCurrentImageContext()
        UIGraphicsEndImageContext()
        let pickerModel = STImagePickerModel()
        pickerModel.editedImage = newImage
        pickerModel.originalImage = originalImage
        pickerModel.openSourceError = .openSourceOK
        if let complection = self.imagePickerDidFinish {
            complection(pickerModel)
        }
        picker.dismiss(animated: true) {}
    }

    public func imagePickerControllerDidCancel(_ picker: UIImagePickerController) {
        let pickerModel = STImagePickerModel()
        pickerModel.openSourceError = .imagePickerControllerDidCancelError
        if let complection = self.imagePickerDidFinish {
            complection(pickerModel)
        }
        picker.dismiss(animated: true) {}
    }
}
