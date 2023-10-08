package com.flapkap.vending.infrastructure.security.model;

public interface UserAccessPermissions {

  String HAS_SELLER_ROLE = "hasAnyAuthority('ROLE_SELLER')";
  String HAS_BUYER_ROLE = "hasAnyAuthority('ROLE_BUYER')";
  String HAS_SELLER_OR_BUYER_ROLE = "hasAnyAuthority('ROLE_SELLER', 'ROLE_BUYER')";
}
