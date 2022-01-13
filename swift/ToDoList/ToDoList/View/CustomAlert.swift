//
//  CustomAlert.swift
//  ToDoList
//
//  Created by wh on 2022/1/13.
//

import SwiftUI

public func TextFieldAlert(title : String = "标题", message : String = "", placeholder : String = " 请输入...", onConfirm : @escaping (String) -> () = { _ in }) {
    let alert = UIAlertController(title: title, message: message, preferredStyle: .alert)
    alert.addTextField() { textField in
        textField.placeholder = placeholder
    }
    alert.addAction(UIAlertAction(title: "取消", style: .cancel) { _ in })
    alert.addAction(UIAlertAction(title: "添加", style: .default) { _ in
        onConfirm(alert.textFields?.first?.text ?? "")
    })
    showAlert(alert: alert)
}

public func ErrorAlert() {
    let alert = UIAlertController(title: "提示", message: "添加失败，分组已存在", preferredStyle: .alert)
    alert.addAction(UIAlertAction(title: "知道了", style: .cancel) { _ in })
    showAlert(alert: alert)
}

func showAlert(alert: UIAlertController) {
    if let controller = topMostViewController() {
        controller.present(alert, animated: true)
    }
}

private func keyWindow() -> UIWindow? {
    return UIApplication.shared.connectedScenes
        .filter {$0.activationState == .foregroundActive}
        .compactMap {$0 as? UIWindowScene}
        .first?.windows.filter {$0.isKeyWindow}.first
}

private func topMostViewController() -> UIViewController? {
    guard let rootController = keyWindow()?.rootViewController else {
        return nil
    }
    return topMostViewController(for: rootController)
}

private func topMostViewController(for controller: UIViewController) -> UIViewController {
    if let presentedController = controller.presentedViewController {
        return topMostViewController(for: presentedController)
    } else if let navigationController = controller as? UINavigationController {
        guard let topController = navigationController.topViewController else {
            return navigationController
        }
        return topMostViewController(for: topController)
    } else if let tabController = controller as? UITabBarController {
        guard let topController = tabController.selectedViewController else {
            return tabController
        }
        return topMostViewController(for: topController)
    }
    return controller
}
